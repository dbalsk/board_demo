package com.codingrecipe.demo.dto;

import com.codingrecipe.demo.entity.BoardEntity;
import lombok.*;

import java.time.LocalDateTime;

//DTO (data transfer object) (=vo,bean)
@Getter
@Setter
@ToString
@NoArgsConstructor // 기본생성자
@AllArgsConstructor // 모든 필드를 매개변수로 하는 생성자
//lombok을 사용하여 자동으로 생성 (getter, setter, 생성자 등)
public class BoardDTO {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits; //게시글 조회수
    private LocalDateTime boardCreatedTime; //게시글 작성시간
    private LocalDateTime boardUpdatedTime; //게시글 수정시간

    public static BoardDTO toBoardDto(BoardEntity boardEntity){
        BoardDTO boardDTO = new BoardDTO();
        boardDTO.setId(boardEntity.getId());
        boardDTO.setBoardWriter(boardEntity.getBoardWriter());
        boardDTO.setBoardPass(boardEntity.getBoardPass());
        boardDTO.setBoardTitle(boardEntity.getBoardTitle());
        boardDTO.setBoardContents(boardEntity.getBoardContents());
        boardDTO.setBoardHits(boardEntity.getBoardHits());
        boardDTO.setBoardCreatedTime(boardEntity.getCreatedTime());
        boardDTO.setBoardUpdatedTime(boardEntity.getUpdatedTime());
        return boardDTO;
    }
}
