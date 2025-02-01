package com.codingrecipe.demo.repository;

import com.codingrecipe.demo.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {
    //JpaRepository로부터 상속받음. 엔티티와 pk의 타입을 인자로
    //save 등 상속받은 메소드들을 사용할 수 있음.

    @Modifying //update, delete 등의 쿼리를 사용할 때 필수로 필요한 어노테이션
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id=:id123")
    void updateHits(@Param("id123") Long id);
    //update 엔티티이름 약어 set 약어.엔티티의컬럼 where 약어.엔티티의id=:Param에 매칭해줄 이름.  -> 엔티티 기준의 쿼리
    //update board_table set board_hits=board_hits+1 where id=?     -> DB(mysql)기준의 쿼리

}
