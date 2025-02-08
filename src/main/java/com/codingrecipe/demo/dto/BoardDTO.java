package com.codingrecipe.demo.dto;

import com.codingrecipe.demo.entity.BoardEntity;
import com.codingrecipe.demo.entity.BoardFileEntity;
import lombok.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

//DTO (data transfer object) (=vo,bean)
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
//lombok을 사용하여 자동으로 생성 (getter, setter, 생성자 등)
public class BoardDTO {
    private Long id;
    private String boardWriter; //게시글 작성자
    private String boardPass; //게시글 비밀번호
    private String boardTitle; //게시글 제목
    private String boardContents; //게시글 내용
    private int boardHits; //게시글 조회수
    private LocalDateTime boardCreatedTime; //게시글 작성시간
    private LocalDateTime boardUpdatedTime; //게시글 수정시간

    private List<MultipartFile> boardFile; //save.html에서 컨트롤러로 이동할 때 파일 담는 용도
    //리스트를 붙여줄 경우 다중파일
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; //서버 저장용 파일 이름 (중복방지를 위해)
    private int fileAttached; //파일 첨부 여부(첨부 1, 미첨부 0)


    public BoardDTO(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDTO toBoardDto(BoardEntity boardEntity){
        //엔티티 -> dto로 변환
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        if(boardEntity.getFileAttached() == 0){
            boardDTO.setFileAttached(boardEntity.getFileAttached()); //0
        }else{
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDTO.setFileAttached(boardEntity.getFileAttached()); //1
            //추가로 파일이름을 가져와야함. 그런데 파일이름들은 board_file_table(파일엔티티)에 저장해둠.
            //변환을 위해 가져온 객체는 엔티티 객체이기에 파일이름이 없음.
            // -> 현재 파일엔티티와 엔티티를 매핑해뒀기에 join 쿼리를 사용하여 가져올 수 있음 (스프링부트 jpa의 장점)
            //select * from board_table b, board_file_table bf where b.id=bf.board_id and where b.id=?

            /* 단일파일
            boardDTO.setOriginalFileName(boardEntity.getBoardFileEntityList().get(0).getOriginalFileName());
            boardDTO.setStoredFileName(boardEntity.getBoardFileEntityList().get(0).getStoredFileName());
            //자식데이터인 보드엔티티 리스트에 접근하여 해당 인덱스(get(i)) 객체의 파일이름 꺼내오기.
            */

            for(BoardFileEntity boardFileEntity : boardEntity.getBoardFileEntityList()){
                originalFileNameList.add(boardFileEntity.getOriginalFileName());
                storedFileNameList.add(boardFileEntity.getStoredFileName());
            }
            boardDTO.setOriginalFileName(originalFileNameList);
            boardDTO.setStoredFileName(storedFileNameList);
        }
        return boardDTO;
    }
}
