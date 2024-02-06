package persi.makeboard.board.dto;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.domain.BoardFile;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    private List<MultipartFile> boardFile; // save.thml -> controller 파일 담는 용도
    private List<String> originalFileName; // 원본 파일 이름
    private List<String> storedFileName; // 서버 저장용 파일 이름 -> 파일명 중복문제 해소
    private int fileAttached; // 파일 첨부 여부(첨부 1, 미첨부 0)


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
        if (board.getFileAttached() == 0){
            boardDto.setFileAttached(board.getFileAttached()); // 0
        } else{
            List<String> originalFileNameList = new ArrayList<>();
            List<String> storedFileNameList = new ArrayList<>();
            boardDto.setFileAttached(board.getFileAttached()); // 1
            // 파일 이름을 가져가야 함
            // originalFileName, storedFileName: board_file_table(BoardFile)
            // join
            // select * from board_table b, board_file_table bf where b.id=bf.board_id and where b.id=?
            for (BoardFile boardFile: board.getBoardFileList()) {
                originalFileNameList.add(boardFile.getOriginalFileName());
                storedFileNameList.add(boardFile.getStoredFileName());
            }
            boardDto.setOriginalFileName(originalFileNameList);
            boardDto.setStoredFileName(storedFileNameList);
        }
        return boardDto;
    }
}
