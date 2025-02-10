package com.codingrecipe.demo.service;

import com.codingrecipe.demo.dto.CommentDTO;
import com.codingrecipe.demo.entity.BoardEntity;
import com.codingrecipe.demo.entity.CommentEntity;
import com.codingrecipe.demo.repository.BoardRepository;
import com.codingrecipe.demo.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDTO commentDto){
        //부모엔티티(boardEntity) 조회
        Optional<BoardEntity> optionalBoardEntity = boardRepository.findById(commentDto.getBoardId());

        if(optionalBoardEntity.isPresent()){
            //부모엔티티가 조회된 경우 댓글저장처리 진행
            BoardEntity boardEntity = optionalBoardEntity.get();
            CommentEntity commentEntity = CommentEntity.toSaveEntity(commentDto, boardEntity);
            return commentRepository.save(commentEntity).getId();
        }else{
            //부모엔티티가 조회안된 경우 null 리턴.
            return null;
        }

    }

    public List<CommentDTO> findAll(Long boardId) {
        //select * from comment_table where board_id=? order by id desc;
        //게시글(board_id) 기준으로 전체 댓글목록(comment_table)을 가져오고 id 기준으로 내림차순(최신부터) 정렬.
        BoardEntity boardEntity = boardRepository.findById(boardId).get();
        List<CommentEntity> commentEntityList = commentRepository.findAllByBoardEntityOrderByIdDesc(boardEntity);

        //dto로 변환
        List<CommentDTO> commentDTOList = new ArrayList<>();
        for(CommentEntity commentEntity: commentEntityList){
            CommentDTO commentDTO= CommentDTO.toCommentDTO(commentEntity, boardId);
            commentDTOList.add(commentDTO);
        }
        return commentDTOList;
    }
}
