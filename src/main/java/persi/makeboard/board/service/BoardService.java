package persi.makeboard.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.board.repository.BoardRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Dto(controller에서 받음) -> Entity(repository로 넘겨줌): Entity class
// Entity(repository에서 받음) -> Dto(controller로 넘겨줌): Dto class
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;

    // 게시물 작성
    public void save(BoardDto boardDto) {
        Board board = Board.toSaveEntity(boardDto);
        boardRepository.save(board);
    }

    // 게시물 목록 조회
    public List<BoardDto> findAll() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();
        for(Board board: boardList) {
            boardDtoList.add(BoardDto.toBoardDto(board));
        }
        return boardDtoList;
    }

    // 게시물 조회수 올리기
    @Transactional
    public void updateHits(Long id) {
        boardRepository.updateHits(id);
    }

    // id로 게시물 데이터 찾기
    public BoardDto findById(Long id) {
        Optional<Board> optionalBoard = boardRepository.findById(id);
        if (optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            BoardDto boardDto = BoardDto.toBoardDto(board);
            return boardDto;
        } else return null;
    }

    // 게시물 수정
    public BoardDto update(BoardDto boardDto) {
        Board board = Board.toUpdateEntity(boardDto);
        boardRepository.save(board);
        return findById(boardDto.getId());
    }

    public void delete(Long id) {
        boardRepository.deleteById(id);
    }
}
