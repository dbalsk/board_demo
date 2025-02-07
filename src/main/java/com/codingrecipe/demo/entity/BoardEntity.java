package com.codingrecipe.demo.entity;

//엔티티: DB의 테이블 역할 (도메인 계층)

import com.codingrecipe.demo.dto.BoardDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table") //@Table을 사용하면 해당 이름의 table 생성 (db에서 생성할 필요x)
public class BoardEntity extends BaseEntity{
    @Id // pk 칼럼 지정. 필수로 있어야함
    @GeneratedValue(strategy = GenerationType.IDENTITY) //auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기 20, not null
    private String boardWriter; //게시글 작성자

    @Column // default는 크기 255, null ok
    private String boardPass; //게시글 비밀번호

    @Column
    private String boardTitle; //게시글 제목

    @Column(length = 500)
    private String boardContents; //게시글 내용

    @Column
    private int boardHits; //게시글 조회수

    @Column
    private int fileAttached; //1 or 0

    //mappedBy는 파일엔티티의 이름과 매핑.
    @OneToMany(mappedBy = "boardEntity",cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>();

/*    @Column
    private LocalDateTime boardCreatedTime; //게시글 작성시간

    @Column
    private LocalDateTime boardUpdatedTime; //게시글 수정시간*/

    public static BoardEntity toSaveEntity(BoardDTO boardDTO){
        //dto -> 엔티티로
        //하지만 엔티티에서 dto를 사용하는것은 ddd설계에 위배될듯함. (도메인이 응용계층을 의존하기에)
        //실제 설계 시에는 dto에서 두 변환작업을 다 해주는 것이 좋을듯.
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(0); //파일없음
        return boardEntity;
    }

    public static BoardEntity toUpdateEntity(BoardDTO boardDTO) {
        //update를 위한 변환 (id 추가)
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setId(boardDTO.getId()); //id 적용
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(boardDTO.getBoardHits()); //히트값을 가져와서 그대로 적용
        return boardEntity;
    }

    public static BoardEntity toSaveFileEntity(BoardDTO boardDTO) {
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setBoardWriter(boardDTO.getBoardWriter());
        boardEntity.setBoardPass(boardDTO.getBoardPass());
        boardEntity.setBoardTitle(boardDTO.getBoardTitle());
        boardEntity.setBoardContents(boardDTO.getBoardContents());
        boardEntity.setBoardHits(0);
        boardEntity.setFileAttached(1); //파일있음
        return boardEntity;
    }
}
