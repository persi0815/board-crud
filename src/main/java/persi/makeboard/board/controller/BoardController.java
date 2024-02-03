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

    // 게시글 상세 조회
    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model) { // 경로상에 있는 값 가져올땐 PathVariable사용
        // 해당 게시글의 조회수를 하나 올리기
        boardService.updateHits(id);
        // 게시글 데이터를 가져와서 detail.html에 출력
        BoardDto boardDto = boardService.findById(id);
        model.addAttribute("board", boardDto);
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

}
