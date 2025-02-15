package com.codingrecipe.demo.entity;

import com.codingrecipe.demo.dto.MemberDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Setter
@Getter
@Table(name="member_table")
public class MemberEntity {
    @Id //pk 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(unique = true) //unique 제약조건 추가
    private String memberEmail;

    @Column
    private String memberPassword;

    @Column
    private String memberName;

    public static MemberEntity toMemberEntity(MemberDTO memberDTO){
        //dto -> 엔티티
        //엔티티에서 위 작업을 수행해는 것은 좋지 않은 설계
        //실제 설계 시, dto에서 두 변환작업을 다 해주거나 서비스에서 매퍼클래스(modelmapper 등)을 사용하여 변환수행하는 것이 좋음.
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }

    public static MemberEntity toUpdateMemberEntity(MemberDTO memberDTO){
        MemberEntity memberEntity = new MemberEntity();
        memberEntity.setId(memberDTO.getId()); //update를 위한 id 추가
        memberEntity.setMemberEmail(memberDTO.getMemberEmail());
        memberEntity.setMemberPassword(memberDTO.getMemberPassword());
        memberEntity.setMemberName(memberDTO.getMemberName());
        return memberEntity;
    }
}
