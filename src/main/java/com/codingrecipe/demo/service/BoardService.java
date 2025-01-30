package com.codingrecipe.demo.service;

import com.codingrecipe.demo.dto.BoardDTO;
import com.codingrecipe.demo.entity.BoardEntity;
import com.codingrecipe.demo.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

//dto <-> 엔티티 변환 작업 수행. (변환 방법은 여러가지. - 생성자, modelmapper 등등)

@Service
@RequiredArgsConstructor //final필드의 생성자 자동생성
public class BoardService {
    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {
        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity); //jparepository에서 상속받은 save 메소드
    }
}
