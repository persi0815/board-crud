package persi.makeboard.board.domain;

import jakarta.persistence.*;
import lombok.CustomLog;
import lombok.Getter;
import lombok.Setter;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.global.entity.BaseEntity;

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

    public static Board toSaveEntity(BoardDto boardDto){
        // Dto에 담겨져 온 값들을 Entity 객체로 옮겨담는 작업
        Board board = new Board();
        board.setBoardWriter(boardDto.getBoardWriter());
        board.setBoardPass(boardDto.getBoardPass());
        board.setBoardTitle(boardDto.getBoardTitle());
        board.setBoardContents(boardDto.getBoardContents());
        board.setBoardHits(0);
        return board;
    }
}
