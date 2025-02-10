package com.codingrecipe.demo.controller;

import com.codingrecipe.demo.dto.CommentDTO;
import com.codingrecipe.demo.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/save")
    public ResponseEntity save(@ModelAttribute CommentDTO commentDto){
        System.out.println("commentDto = "+ commentDto);
        Long saveResult = commentService.save(commentDto);
        if (saveResult != null){
            //작성성공하면 댓글목록을 가져와서 리턴
            List<CommentDTO> commentDTOList = commentService.findAll(commentDto.getBoardId());
            return new ResponseEntity<>(commentDTOList, HttpStatus.OK);
            //ResponseEntity를 사용함으로써 상태코드도 리턴
        }else{
            return new ResponseEntity<>("해당 게시물이 존재하지 않습니다.", HttpStatus.NOT_FOUND);
        }
    }

}
