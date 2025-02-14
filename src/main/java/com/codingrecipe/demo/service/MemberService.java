package com.codingrecipe.demo.service;

import com.codingrecipe.demo.dto.MemberDTO;
import com.codingrecipe.demo.entity.MemberEntity;
import com.codingrecipe.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }
}
