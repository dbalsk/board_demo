package com.codingrecipe.demo.controller;

import com.codingrecipe.demo.dto.BoardDTO;
import com.codingrecipe.demo.service.BoardService;
import lombok.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
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
    public String save(@ModelAttribute BoardDTO boardDTO) throws IOException {
        //게시글 작성 기능 메소드
        //@ModelAttribute로 해당 클래스 객체를 매핑해줌. (사용자가 넣어준  폼 데이터를 객체로 바인딩)
        //form태그의 name과 클래스 필드의 이름이 같을 경우 스프링에서 자동으로 setter를 실행하여 넣어줌
        System.out.println("boardDTO =" + boardDTO); //확인용
        boardService.save(boardDTO);
        return "index"; //index.html로
    }

    @GetMapping("/")
    public String findAll(Model model) {
        //게시글 목록 기능 메소드
        //DB에서 전체 게시글 데이터를 가져와서 list.html에 보여주기
        List<BoardDTO> boardDtoList=boardService.findAll();
        model.addAttribute("boardList", boardDtoList); //'boardList' 이름으로 리스트 저장.
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable){
        //게시글 상세 조회 기능 메소드
        //게시글 조회수 1 올리고 게시글의 데이터를 가져와서 detail.html에 출력
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);

        //02.05 -> 현재 페이지값을 추가
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        //게시글 수정내용 입력 기능 메소드
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model){
        //게시글 수정내용 저장 및 출력 기능 메소드
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board); //수정을 완료한 후 해당 게시글의 상세내용 조회.
        return "detail";
        //return "redirect:/board/" + boardDTO.getId();
        //위와같이 그냥 리다이렉트해서 상세조회를 해줘도 되지만 조회수에 영향을 주기에 "detail"을 보여주는 방식 사용
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        //게시글 삭제 기능 메소드
        boardService.delete(id);
        return "redirect:/board/"; //목록 출력
    }

    @GetMapping("/paging")
    public String paging(@PageableDefault(page=1) Pageable pageable, Model model){
        //페이지 관련 애노테이션 활용
        //주의) Pageable 임포트 시 자바의 Pageable 사용x 스프링의 Pageable 사용해야됨.
        //pageable.getPageNumber(); //page의 값 받아오기. ex) /board/paging?page=1 -> 1
        Page<BoardDTO> boardList = boardService.paging(pageable); //페이지 객체 생성

        int blockLimit = 3; //보여지는 페이지의 개수 ex) 5 -> 1 2 3 4 5
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        //시작 페이지 계산식 -> 위 경우 1 4 7 10 ~~ (Math.ceil은 소수점 올려주기)
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        //엔드 페이지 계산식 -> 위 경우 3 6 9 12 (삼항연산식 - 전체페이지보다 작은 값이라면 전체페이지를 엔드 페이지로)

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }
}
