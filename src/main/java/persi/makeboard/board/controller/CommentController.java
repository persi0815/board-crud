package persi.makeboard.board.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import persi.makeboard.board.dto.CommentDto;

@Controller
@RequiredArgsConstructor
@RequestMapping("/comment")
public class CommentController {
    @PostMapping("/save")
    public @ResponseBody String save(@ModelAttribute CommentDto commentDto){
        System.out.println("commentDto = " + commentDto);
        return "요청 성공";
    }
}
