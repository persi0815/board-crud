package persi.makeboard.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import persi.makeboard.board.dto.CommentDto;

@Entity
@Getter
@Setter
@Table(name = "comment_table")
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 20, nullable = false)
    private String commentWriter;

    @Column
    private String commentContents;

    //Board:Comment = 1:N
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "board_id")
    private Board board;

    public static Comment toSaveEntity(CommentDto commentDto, Board board) {
        Comment comment = new Comment();
        comment.setCommentWriter(commentDto.getCommentWriter());
        comment.setCommentContents(commentDto.getCommentContents());
        comment.setBoard(board); // 게시글 번호로 조회한 부모 엔티티
        return comment;
    }
}
