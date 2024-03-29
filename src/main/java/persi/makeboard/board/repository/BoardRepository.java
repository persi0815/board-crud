package persi.makeboard.board.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import persi.makeboard.board.domain.Board;

public interface BoardRepository extends JpaRepository<Board, Long> {
    // update board_table set board_hits=board_hits+1 where id=?
    @Modifying
    @Query(value = "update Board b set b.boardHits=b.boardHits+1 where b.id=:id")
    void updateHits(@Param("id") Long id);
}
