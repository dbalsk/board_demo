package com.codingrecipe.demo.dto;

import com.codingrecipe.demo.entity.CommentEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDTO {
    private Long id; //댓글의 id
    private String commentWriter;
    private String commentContents;
    private Long boardId; //게시글번호 (중요)
    private LocalDateTime commentCreatedTime;

    public static CommentDTO toCommentDTO(CommentEntity commentEntity, Long boardId) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.setId(commentEntity.getId());
        commentDTO.setCommentWriter(commentEntity.getCommentWriter());
        commentDTO.setCommentContents(commentEntity.getCommentContents());
        commentDTO.setCommentCreatedTime(commentEntity.getCreatedTime());
        //commentDTO.setBoardId(commentEntity.getBoardEntity().getId()); //서비스에서 @Transactional 필요
        commentDTO.setBoardId(boardId); //위 코드와 동일
        return commentDTO;
    }
}
