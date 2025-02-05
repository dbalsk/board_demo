package com.codingrecipe.demo.service;

import com.codingrecipe.demo.dto.BoardDTO;
import com.codingrecipe.demo.entity.BoardEntity;
import com.codingrecipe.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

//dto <-> 엔티티 변환 작업 수행. (변환 방법은 여러가지. - 생성자, modelmapper 등등)

@Service
@RequiredArgsConstructor //final필드의 생성자 자동생성
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); //jpaRepository에서 상속받은 save 메소드
    }

    public List<BoardDTO> findAll() {
        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
        for(BoardEntity boardEntity :boardEntityList){
            boardDTOList.add(BoardDTO.toBoardDto(boardEntity));
        }
        return boardDTOList;
    }

    @Transactional //직접 정의한 메소드에는 트랜잭셔널 어노테이션 붙여야함
    public void updateHits(Long id) {
        //위와같은 특수한 기능의 메소드는 jpa가 자동으로 쿼리를 제공해주지 않기에 레포지토리에 별도로 정의 필요.
        boardRepository.updateHits(id);
    }


    public BoardDTO findById(Long id) {
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(id);
        if(optionalBoardEntity.isPresent()) { //객체가 있다면
            BoardEntity boardEntity = optionalBoardEntity.get(); //엔티티로 주고
            BoardDTO boardDTO = BoardDTO.toBoardDto(boardEntity); //dto로 변환
            return boardDTO;
        }else{
            return null;
        }
    }

    public BoardDTO update(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toUpdateEntity(boardDTO);
        boardRepository.save(boardEntity); //jpa에서 save는 insert와 update에 모두 사용됨. 구분 방법은 id의 사용유무
        //**id**가 사용될 경우 jpa에서 update로 판단
        return findById(boardEntity.getId()); //findById를 재사용하여 변환코드 생략하고 해당 id의 dto를 반환.
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    public Page<BoardDTO> paging(Pageable pageable) {
        int page=pageable.getPageNumber()-1; //사용자가 어떤 페이지를 볼지 결정. (page는 0부터 시작하기에 -1해줌)
        int pageLimit = 3; //한 페이지에 보여줄 글 개수
        //한페이지 당 3개의 글을 보여주고 정렬기준은 id 기준으로 내림차순 정렬.     ("id"는 엔티티의 이름)
        Page<BoardEntity> boardEntities = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));;

        //Page 객체로 선언해야 위와 같은 메소드를 사용할 수 있음.
        System.out.println("boardEntities.getContent() = " + boardEntities.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boardEntities.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boardEntities.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boardEntities.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boardEntities.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boardEntities.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boardEntities.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boardEntities.isLast()); // 마지막 페이지 여부

        //엔티티의 값을 map 메소드(Page객체의 메소드)를 통해 dto로 옮겨담을 수 있음. (위의 페이지 정보들도 옮겨짐)
        //목록에는 id, writer, title, hits, createdTime을 보여주면됨. -> 위 필드를 포함하는 DTO 생성자 생성
        Page<BoardDTO> boardDTOS = boardEntities.map(board -> new BoardDTO(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDTOS;
    }
}
