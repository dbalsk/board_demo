package com.codingrecipe.demo.repository;

import com.codingrecipe.demo.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<MemberEntity, Long> { //pk의 타입
}
