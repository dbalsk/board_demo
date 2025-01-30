package com.codingrecipe.demo.controller;

import com.codingrecipe.demo.dto.BoardDTO;
import com.codingrecipe.demo.service.BoardService;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor //lombok에서 제공하는 이노테이션으로 final 필드의 생성자 자동생성
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService; //생성자 자동생성하여 의존성 주입

    @GetMapping("/save")
    public String saveForm(){
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO){
        //@ModelAttribute로 해당 클래스 객체를 매핑해줌.
        //form태그의 name과 클래스 필드의 이름이 같을 경우 스프링에서 자동으로 setter를 실행하여 넣어줌
        System.out.println("boardDTO =" + boardDTO); //확인용
        boardService.save(boardDTO);
        return "index";
    }
}
