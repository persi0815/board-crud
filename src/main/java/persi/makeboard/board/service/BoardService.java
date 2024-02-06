package persi.makeboard.board.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.domain.BoardFile;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.board.repository.BoardFileRepository;
import persi.makeboard.board.repository.BoardRepository;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Dto(controller에서 받음) -> Entity(repository로 넘겨줌): Entity class
// Entity(repository에서 받음) -> Dto(controller로 넘겨줌): Dto class
@Service
@RequiredArgsConstructor
public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardFileRepository boardFileRepository;

    // 게시물 작성
    public void save(BoardDto boardDto) throws IOException {
        // 파일 첨부 여부에 따라 로직 분리
        if (boardDto.getBoardFile().isEmpty()){
            // 첨부 파일이 없음
            Board board = Board.toSaveEntity(boardDto);
            boardRepository.save(board);
        } else{
            // 첨부 파일이 있음
            /*
                1. Dto에 담기 파일을 꺼냄
                2. 파일의 이름 가져옴
                3. 서버 저장용 이름을 만듦
                // 내사진.jpa => 난수_내사진.jpg
                4. 저장 경로 설정
                5. 해당 경로에 파일 저장
                6. board_table에 해당 데이터 save 처리
                7. board_file_table에 해당 데이터 save 처리
             */
            Board boardEntity = Board.toSaveFileEntity(boardDto);
            Long savedId = boardRepository.save(boardEntity).getId();
            Board board = boardRepository.findById(savedId).get();

            for (MultipartFile boardFile: boardDto.getBoardFile()) {
//                MultipartFile boardFile = boardDto.getBoardFile(); // 1.
                String originalFilename = boardFile.getOriginalFilename(); // 2.
                String storedFileName = System.currentTimeMillis() + "_" + originalFilename; // 3.
                String savePath = "C:/Users/양지원/OneDrive/바탕 화면/springboot_img/" + storedFileName; //4. C:/Users/양지원/OneDrive/바탕 화면/springboot_img//338493020_내사진.jpg
                boardFile.transferTo(new File(savePath)); // 5.

                BoardFile boardFileEntity = BoardFile.toBoardFileEntity(board, originalFilename, storedFileName);
                boardFileRepository.save(boardFileEntity);
            }


        }
    }

    @Transactional
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

    @Transactional
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
