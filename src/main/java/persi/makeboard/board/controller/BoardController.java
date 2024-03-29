package persi.makeboard.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.board.dto.CommentDto;
import persi.makeboard.board.service.BoardService;
import persi.makeboard.board.service.CommentService;

import java.io.IOException;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;
    private final CommentService commentService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    // 게시물 작성
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto) throws IOException {
        System.out.println("boardDto = " + boardDto);
        boardService.save(boardDto);
        return "index";
    }

    // 게시물 목록 조회
    @GetMapping("/")
    public String findAll(Model model){ //데이터를 가져올때 model 객체 사용
        // DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다
        List<BoardDto> boardDtoList = boardService.findAll();
        model.addAttribute("boardList", boardDtoList);
        return "list";
    }

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model, @PageableDefault(page=1) Pageable pageable) { // 경로상에 있는 값 가져올땐 PathVariable사용
        // 해당 게시글의 조회수를 하나 올리기
        boardService.updateHits(id);
        // 게시글 데이터를 가져와서 detail.html에 출력
        BoardDto boardDto = boardService.findById(id);
        // 댓글 목록 가져오기
        List<CommentDto> commentDtoList = commentService.findAll(id);
        model.addAttribute("commentList", commentDtoList);

        model.addAttribute("board", boardDto);
        model.addAttribute("page", pageable.getPageNumber());
        return "detail";
    }

    // 게시글 수정 ing
    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model){
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDto);
        return "update";
    }

    // 게시글 수정 end
    @PostMapping("/update")
    public String update(@ModelAttribute BoardDto boardDto, Model model){
        BoardDto board = boardService.update(boardDto);
        model.addAttribute("board", board);
        return "detail"; //수정 반영된 객체 가지고 화면에 띄움
        //return "redirect: /board/" + boardDto.getId(); // 상세 페이지 요청을 위한 리다이렉트
    }

    // 게시글 삭제
    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id){
        boardService.delete(id);
        return "redirect:/board/"; // 띄어쓰기 하면 안됨
    }

    // 페이징
    @GetMapping("/paging") // 기본적으로 1페이지 보여줌
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model){
        Page<BoardDto> boardList = boardService.paging(pageable);

        int blockLimit = 3; // 보여지는 페이지 번호 개수
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = Math.min((startPage + blockLimit - 1), boardList.getTotalPages()); // 3 6 9 ~~

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";
    }

}
