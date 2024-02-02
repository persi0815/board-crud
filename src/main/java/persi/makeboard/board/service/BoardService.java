package persi.makeboard.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.board.repository.BoardRepository;

// Dto(controller에서 받음) -> Entity(repository로 넘겨줌): Entity class
// Entity(repository에서 받음) -> Dto(controller로 넘겨줌): Dto class
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    public void save(BoardDto boardDto) {
        Board board = Board.toSaveEntity(boardDto);
        boardRepository.save(board);
    }
}
