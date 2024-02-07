package persi.makeboard.board.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import persi.makeboard.board.domain.Board;
import persi.makeboard.board.dto.CommentDto;
import persi.makeboard.board.repository.BoardRepository;
import persi.makeboard.board.repository.CommentRepository;
import persi.makeboard.board.domain.Comment;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final BoardRepository boardRepository;

    public Long save(CommentDto commentDto) {
        // 부모엔티티(Board) 조회
        Optional<Board> optionalBoard = boardRepository.findById(commentDto.getBoardId());
        if (optionalBoard.isPresent()){
            Board board = optionalBoard.get();
            Comment comment = Comment.toSaveEntity(commentDto, board);
            return commentRepository.save(comment).getId();
        } else {
            return null;
        }
    }

    public List<CommentDto> findAll(Long boardId) {
        // select * from comment_table where board_id =? order by id desc; // 최근 작성한 댓글이 먼저 보이도록
        Board board = boardRepository.findById(boardId).get();
        List<Comment> commentList = commentRepository.findAllByBoardOrderByIdDesc(board);
        // EntityList -> DtoList
        List<CommentDto> commentDtoList = new ArrayList<>();
        for (Comment comment: commentList){
            CommentDto commentDto = CommentDto.toCommentDto(comment, boardId);
            commentDtoList.add(commentDto);
        }
        return commentDtoList;
    }
}
