package com.example.tem;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/posts")
public class PostController {
    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return """
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <br>
                    <textarea name="content" placeholder="내용"></textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """;
    }

    @PostMapping("/write")
    @ResponseBody
    public String write(String title, String content) {
        if (title == null || title.isBlank()) {
            return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <br>
                    <textarea name="content" placeholder="내용"></textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """.formatted("제목을 입력하세요.");
        }

        if (title.length() < 5) {
            return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <br>
                    <textarea name="content" placeholder="내용"></textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """.formatted("제목을 5자 이상 입력하세요.");
        }

        if (content == null || content.isBlank()) {
            return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <br>
                    <textarea name="content" placeholder="내용"></textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """.formatted("내용을 입력하세요.");
        }

        if (content.length() < 10) {
            return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목">
                    <br>
                    <textarea name="content" placeholder="내용"></textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """.formatted("내용을 10자 이상 입력하세요.");
        }

        return """
                <h1>글쓰기 완료</h1>
                
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(title, content);
    }
}
