package persi.makeboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import persi.makeboard.board.domain.BoardFile;

public interface BoardFileRepository extends JpaRepository<BoardFile, Long> {

}
