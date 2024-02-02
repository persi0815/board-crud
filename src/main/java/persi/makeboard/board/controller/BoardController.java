package persi.makeboard.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import persi.makeboard.board.dto.BoardDto;
import persi.makeboard.board.service.BoardService;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/board")
public class BoardController {
    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    // 게시물 작성
    @PostMapping("/save")
    public String save(@ModelAttribute BoardDto boardDto) {
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
}
