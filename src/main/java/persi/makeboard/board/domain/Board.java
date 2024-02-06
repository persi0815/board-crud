package persi.makeboard.board.domain;

import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.global.entity.BaseEntity;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "board_table")
public class Board extends BaseEntity {
    @Id // pk colum 지정. 필수
    @GeneratedValue(strategy = GenerationType.IDENTITY) // auto_increment
    private Long id;

    @Column(length = 20, nullable = false) // 크기는 20, not null
    private String boardWriter;

    @Column // default: 크기 255, null 가능
    private String boardPass;

    @Column
    private String boardTitle;

    @Column(length = 500)
    private String boardContents;

    @Column
    private int boardHits;

    @Column
    private int fileAttached; // 1 or 0

    @OneToMany(mappedBy = "board", cascade = CascadeType.REMOVE, orphanRemoval = true, fetch = FetchType.LAZY) // 게시물 삭제시 파일도 사라짐
    private List<BoardFileEntity> boardFileEntityList = new ArrayList<>(); // file 여러개 -> list형태로 가져옴

    public static Board toSaveEntity(BoardDto boardDto){
        // Dto에 담겨져 온 값들을 Entity 객체로 옮겨담는 작업
        Board board = new Board();
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardPass(boardDto.getBoardPass());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContents(boardDto.getBoardContents());
        board.setBoardHits(0);
        board.setFileAttached(0); // 파일 없음
        return board;
    }

    public static Board toUpdateEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setId(boardDto.getId()); // ID가 있어야만 업데이트 쿼리가 전달될 수 있음
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardPass(boardDto.getBoardPass());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContents(boardDto.getBoardContents());
        board.setBoardHits(boardDto.getBoardHits()); // 조회수 가져오기
        return board;
    }

    public static Board toSaveFileEntity(BoardDto boardDto) {
        Board board = new Board();
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardPass(boardDto.getBoardPass());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContents(boardDto.getBoardContents());
        board.setBoardHits(0);
        board.setFileAttached(1); // 파일 있음
        return board;
    }
}
