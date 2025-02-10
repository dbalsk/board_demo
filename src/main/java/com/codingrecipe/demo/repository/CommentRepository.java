package com.codingrecipe.demo.repository;

import com.codingrecipe.demo.entity.BoardEntity;
import com.codingrecipe.demo.entity.CommentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CommentRepository extends JpaRepository<CommentEntity, Long> {
    //select * from comment_table where board_id=? order by id desc;
    List<CommentEntity> findAllByBoardEntityOrderByIdDesc(BoardEntity boardEntity);
    //주의) 변수명을 대소문자 제대로 맞춰 사용해야 제대로 된 쿼리가 생성됨.
}
