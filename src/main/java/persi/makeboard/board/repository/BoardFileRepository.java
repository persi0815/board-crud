package persi.makeboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import persi.makeboard.board.domain.BoardFileEntity;

public interface BoardFileRepository extends JpaRepository<BoardFileEntity, Long> {

}
