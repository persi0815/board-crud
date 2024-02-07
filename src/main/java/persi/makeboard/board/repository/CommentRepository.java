package persi.makeboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.domain.Comment;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    // select * from comment_table where board_id =? order by id desc; // 최근 작성한 댓글이 먼저 보이도록
    List<Comment> findAllByBoardOrderByIdDesc(Board board);

}
