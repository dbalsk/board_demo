package com.codingrecipe.demo.controller;

import com.codingrecipe.demo.dto.MemberDTO;
import com.codingrecipe.demo.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MemberController {
    //생성자 주입
    private final MemberService memberService;

    //회원가입 페이지 출력 요청
    @GetMapping("/member/save")
    public String saveForm(){
        return "save_M";
    }

    //회원가입
    @PostMapping("/member/save")
    public String save(@ModelAttribute MemberDTO memberDTO){
        //System.out.println("memberDTO = " + memberDTO);
        memberService.save(memberDTO);
        return "login";
    }

    //회원가입 페이지 출력 요청
    @GetMapping("/member/login")
    public String loginForm(){
        return "login";
    }

    //로그인
    @PostMapping("/member/login")
    public String login(@ModelAttribute MemberDTO memberDTO, HttpSession session){ //세션에 내 정보를 담기위해 사용
        MemberDTO loginResult = memberService.login(memberDTO);
        if(loginResult != null){
            //로그인 성공
            session.setAttribute("loginEmail", loginResult.getMemberEmail()); //담기
            return "main";
        }else{
            //로그인 실패
            return "login";
        }
    }

    //목록 조회
    @GetMapping("/member/")
    public String findAll(Model model){ //html로 가져갈 데이터가 있을 때 model 객체를 사용하여 담아갈 수 있음.
        List<MemberDTO> memberDTOList = memberService.findAll();
        model.addAttribute("memberList", memberDTOList);
        return "list_M";
    }

    //상세조회
    @GetMapping("/member/{id}")
    public String findById(@PathVariable Long id, Model model){ //특정 자원을 사용하기에 패스베리어블
        MemberDTO memberDTO = memberService.findById(id);
        model.addAttribute("member", memberDTO);
        return "detail_M";
    }

    //수정 요청
    @GetMapping("/member/update")
    public String updateForm(HttpSession session, Model model){
        //이메일로 해당 객체 찾아서 가져오기
        String myEmail = (String) session.getAttribute("loginEmail"); //세션에 담아둔 내 정보를 가져오기
        MemberDTO memberDTO = memberService.updateForm(myEmail);
        model.addAttribute("updateMember", memberDTO);
        return "update_M";
    }

    //수정
    @PostMapping("/member/update")
    public String update(@ModelAttribute MemberDTO memberDTO){
        memberService.update(memberDTO);
        //findById가 했던 것처럼 이메일로 객체를 찾아서 detail로 보내도 되지만
        //리다이렉트하여 다른 컨트롤러 메소드를 사용할 수 있음. (findById 호출하여 detail로 이동.)
        return "redirect:/member/" + memberDTO.getId();
    }

    //삭제
    @GetMapping("/member/delete/{id}")
    public String deleteById(@PathVariable Long id){
        memberService.deleteById(id);
        return "redirect:/member/";
    }

    //로그아웃
    @GetMapping("/member/logout")
    public String logout(HttpSession session){
        session.invalidate(); //세션 무효화
        return "index";
    }

    //이메일 중복체크
    @PostMapping("/member/email-check")
    public @ResponseBody String emailCheck(@RequestParam("memberEmail") String memberEmail){
        String checkResult = memberService.emailCheck(memberEmail);
        return checkResult;
    }
}
