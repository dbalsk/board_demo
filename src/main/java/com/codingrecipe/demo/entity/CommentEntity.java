package com.codingrecipe.demo.entity;

import com.codingrecipe.demo.dto.CommentDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name="comment_table")
public class CommentEntity extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter; //댓글작성자

    @Column
    private String commentContents; //댓글내용

    //Board와 comment는 1:n의 관계 -> 즉 부모와 자식 관계 (파일과 유사)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private BoardEntity boardEntity;

    public static CommentEntity toSaveEntity(CommentDTO commentDto, BoardEntity boardEntity) {
        CommentEntity commentEntity = new CommentEntity();
        commentEntity.setCommentWriter(commentDto.getCommentWriter());
        commentEntity.setCommentContents(commentDto.getCommentContents());
        commentEntity.setBoardEntity(boardEntity); //게시글번호로 조회한 부모엔티티도 넣어줘야함. 참조관계를 맺었을 경우 해줘야함(스프링부트 jpa 문법)
        return commentEntity;
    }
}
