package com.codingrecipe.demo.controller;

import com.codingrecipe.demo.dto.BoardDTO;
import com.codingrecipe.demo.service.BoardService;
import lombok.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        return "index"; //index.html로
    }

    @GetMapping("/")
    public String findAll(Model model) {
        //DB에서 전체 게시글 데이터를 가져와서 list.html에 보여주기
        List<BoardDTO> boardDtoList=boardService.findAll();
        model.addAttribute("boardList", boardDtoList); //'boardList' 이름으로 리스트 저장.
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model){
        //게시글 조회 기능 메소드
        //게시글 조회수 1 올리고 게시글의 데이터를 가져와서 detail.html에 출력
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        return "detail";
    }
}
