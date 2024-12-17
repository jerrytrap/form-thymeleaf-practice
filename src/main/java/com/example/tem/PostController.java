package com.example.tem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/posts")
public class PostController {
    @GetMapping("/write")
    @ResponseBody
    public String write() {
        return """
                <form>
                    <input type="text" name="title" placeholder="제목">
                    <br>
                    <textarea name="content" placeholder="내용"></textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """;
    }
}
