package persi.makeboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import persi.makeboard.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
}
