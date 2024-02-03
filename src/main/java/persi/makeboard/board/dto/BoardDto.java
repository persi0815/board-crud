package persi.makeboard.board.dto;

import lombok.*;
import persi.makeboard.board.domain.Board;

import java.time.LocalDateTime;

// DTO(Data Transfer Object), VO, Bean
@Getter //Get method 자동으로 만들어줌
@Setter //Set method 자동으로 만들어줌
@ToString
@NoArgsConstructor // 기본 생성자
@AllArgsConstructor //모든 필드를 매개변수로 하는 생성자
public class BoardDto {
    private Long id;
    private String boardWriter;
    private String boardPass;
    private String boardTitle;
    private String boardContents;
    private int boardHits;
    private LocalDateTime boardCreatedTime;
    private LocalDateTime boardUpdatedTime;

    public BoardDto(Long id, String boardWriter, String boardTitle, int boardHits, LocalDateTime boardCreatedTime) {
        this.id = id;
        this.boardWriter = boardWriter;
        this.boardTitle = boardTitle;
        this.boardHits = boardHits;
        this.boardCreatedTime = boardCreatedTime;
    }

    public static BoardDto toBoardDto(Board board){
        BoardDto boardDto = new BoardDto();
        boardDto.setId(board.getId());
        boardDto.setBoardWriter(board.getBoardWriter());
        boardDto.setBoardPass(board.getBoardPass());
        boardDto.setBoardTitle(board.getBoardTitle());
        boardDto.setBoardContents(board.getBoardContents());
        boardDto.setBoardHits(board.getBoardHits());
        boardDto.setBoardCreatedTime(board.getCreatedTime());
        boardDto.setBoardUpdatedTime(board.getUpdateTime());
        return boardDto;
    }
}
