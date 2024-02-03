package persi.makeboard.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

    // 게시물 삭제
    public void delete(Long id) {
        boardRepository.deleteById(id);
    }

    // 게시물 페이징
    public Page<BoardDto> paging(Pageable pageable) {
        int page = pageable.getPageNumber() -1 ;
        int pageLimit = 3; // 한 페이지에 보여줄 글 갯수
        // 한 페이지당 3개씩 글을 보여주고 정렬 기준은 id 기준으로 내림차순 정렬
        // page 위치에 있는 값은 0부터 시작. 실제로 보고 싶은 값은 1 -> -1 해줌
        Page<Board> boards = boardRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "id")));
        System.out.println("boardEntities.getContent() = " + boards.getContent()); // 요청 페이지에 해당하는 글
        System.out.println("boardEntities.getTotalElements() = " + boards.getTotalElements()); // 전체 글갯수
        System.out.println("boardEntities.getNumber() = " + boards.getNumber()); // DB로 요청한 페이지 번호
        System.out.println("boardEntities.getTotalPages() = " + boards.getTotalPages()); // 전체 페이지 갯수
        System.out.println("boardEntities.getSize() = " + boards.getSize()); // 한 페이지에 보여지는 글 갯수
        System.out.println("boardEntities.hasPrevious() = " + boards.hasPrevious()); // 이전 페이지 존재 여부
        System.out.println("boardEntities.isFirst() = " + boards.isFirst()); // 첫 페이지 여부
        System.out.println("boardEntities.isLast() = " + boards.isLast()); // 마지막 페이지 여부

        // 목록: id, writer, title, hits, createdTime
        Page<BoardDto> boardDtos = boards.map(board -> new BoardDto(board.getId(), board.getBoardWriter(), board.getBoardTitle(), board.getBoardHits(), board.getCreatedTime()));
        return boardDtos; // Page 객체로 리턴
    }
}
