package persi.makeboard.board.dto;

import lombok.*;

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

}
