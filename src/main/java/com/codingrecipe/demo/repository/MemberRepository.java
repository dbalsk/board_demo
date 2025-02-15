package com.codingrecipe.demo.repository;

import com.codingrecipe.demo.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional; //null 방지를 위한 객체

public interface MemberRepository extends JpaRepository<MemberEntity, Long> { //pk의 타입

    //이메일로 회원정보 조회 (select * from member_table where member_email=?)
    Optional<MemberEntity> findByMemberEmail(String memberEmail);

}
