package com.codingrecipe.demo.service;

import com.codingrecipe.demo.dto.BoardDTO;
import com.codingrecipe.demo.entity.BoardEntity;
import com.codingrecipe.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
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
}
