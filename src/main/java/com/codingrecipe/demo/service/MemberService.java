package com.codingrecipe.demo.service;

import com.codingrecipe.demo.dto.MemberDTO;
import com.codingrecipe.demo.entity.MemberEntity;
import com.codingrecipe.demo.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;

    public void save(MemberDTO memberDTO) {
        MemberEntity memberEntity = MemberEntity.toMemberEntity(memberDTO);
        memberRepository.save(memberEntity);
    }


    public MemberDTO login(MemberDTO memberDTO) {
        //1. 회원이 입력한 이메일로 db에서 조회
        //2. db에서 조회한 비밀번호와 사용자가 입력한 비밀번호가 일치하는지 판단
        //원시적인 방법. 추후 교체 필요.

        //MemberEntity를 사용. (isPresent, get 사용을 위해 Optional로 감싸준 상태.)
        Optional<MemberEntity> byMemberEmail =  memberRepository.findByMemberEmail(memberDTO.getMemberEmail());
        if(byMemberEmail.isPresent()) {
            //조회 결과가 있음. (해당 이메일을 가진 회원정보있음)
            MemberEntity memberEntity = byMemberEmail.get();
            if(memberEntity.getMemberPassword().equals(memberDTO.getMemberPassword())){
                //비밀번호 일치 (해당 이메일의 객체가 가진 비밀번호와 같은지)
                MemberDTO dto = MemberDTO.toMemberDTO(memberEntity);
                return dto;
            }else{
                //비밀번호 불일치
                return null;
            }
        }else{
            //조회 결과가 없음.
            return null;
        }

    }

    public List<MemberDTO> findAll() {
        List<MemberEntity> memberEntityList = memberRepository.findAll();
        List<MemberDTO> memberDTOList = new ArrayList<>();
        for (MemberEntity memberEntity : memberEntityList) {
            memberDTOList.add(MemberDTO.toMemberDTO(memberEntity));
        }
        return memberDTOList;
    }

    public MemberDTO findById(Long id) {
        Optional<MemberEntity> optionalMemberEntity  = memberRepository.findById(id);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public MemberDTO updateForm(String myEmail) {
        Optional<MemberEntity> optionalMemberEntity = memberRepository.findByMemberEmail(myEmail);
        if(optionalMemberEntity.isPresent()){
            return MemberDTO.toMemberDTO(optionalMemberEntity.get());
        }else{
            return null;
        }
    }

    public void update(MemberDTO memberDTO) {
        memberRepository.save(MemberEntity.toUpdateMemberEntity(memberDTO) ); //id가 있을 경우 UPDATE 쿼리 날려줌. (boardService와 동일한 방법)
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }


    public String emailCheck(String memberEmail) {
        Optional<MemberEntity> byMemberEmail = memberRepository.findByMemberEmail(memberEmail);
        if(byMemberEmail.isPresent()){
            //조회결과가 있음 -> 사용x
            return null;
        }else{
            //조회결과가 없음 -> 사용가능
            return "ok";
        }
    }
}
