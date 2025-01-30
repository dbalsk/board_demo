package com.codingrecipe.demo.repository;

import com.codingrecipe.demo.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    //JpaRepository로부터 상속받음. 엔티티와 pk의 타입을 인자로
    //save 등 상속받은 메소드들을 사용할 수 있음.
}
