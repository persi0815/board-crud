package persi.makeboard.board.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import persi.makeboard.board.domain.Comment;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
public class CommentDto {
    private Long id;
    private String commentWriter;
    private String commentContents;
    private Long boardId;
    private LocalDateTime commentCreatedTime;

    public static CommentDto toCommentDto(Comment comment, Long boardId) {
        CommentDto commentDto = new CommentDto();
        commentDto.setId(comment.getId());
        commentDto.setCommentWriter(comment.getCommentWriter());
        commentDto.setCommentContents(comment.getCommentContents());
        commentDto.setCommentCreatedTime(comment.getBoard().getCreatedTime());
        commentDto.setBoardId(boardId);
        // commentDto.setBoardId(comment.getBoard().getId()); // Service 메서드에 @Transactional 붙여야 함
        return commentDto;
    }
}
