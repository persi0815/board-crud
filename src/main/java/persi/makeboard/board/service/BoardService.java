package persi.makeboard.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.board.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;

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

    public List<BoardDto> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board: boardList) {
            boardDtoList.add(BoardDto.toBoardDto(board));
        }
        return boardDtoList;
    }
}
