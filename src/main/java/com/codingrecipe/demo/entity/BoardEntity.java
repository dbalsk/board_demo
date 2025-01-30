package com.codingrecipe.demo.entity;

//엔티티: DB의 테이블 역할 (도메인 계층)

import com.codingrecipe.demo.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Table(name = "board_table") //@Table을 사용하면 해당 이름의 table 생성 (db에서 생성할 필요x)
public class BoardEntity extends BaseEntity{
    @Id // pk 칼럼 지정. 필수로 있어야함
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter;

    @Column // default는 크기 255, null ok
    private String boardPass;

        @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        //하지만 엔티티에서 dto를 사용하는것은 ddd설계에 위배될듯함. (도메인이 응용계층을 의존하기에)
        //실제 설계 시에는 dto에서 두 변환작업을 다 해주는 것이 좋을듯.
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        return boardEntity;
    }
}
