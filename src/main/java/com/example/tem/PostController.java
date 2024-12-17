package com.example.tem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/posts")
public class PostController {
    private List<Post> posts = new ArrayList<>() {{
        add(
                Post.builder()
                        .title("제목1")
                        .content("내용1")
                        .build()
        );

        add(
                Post.builder()
                        .title("제목2")
                        .content("내용2")
                        .build()
        );

        add(
                Post.builder()
                        .title("제목3")
                        .content("내용3")
                        .build()
        );
    }};

    private String getFormHtml(String errorMessage, String title, String content) {
        return """
                <div>%s</div>
                <form method="POST">
                    <input type="text" name="title" placeholder="제목" value="%s">
                    <br>
                    <textarea name="content" placeholder="내용">%s</textarea>
                    <br>
                    <input type="submit" value="생성">
                </form>
                """.formatted(errorMessage, title, content);
    }

    @GetMapping("/list")
    @ResponseBody
    public String showList() {
        String ul = "<ul>" + posts
                .reversed()
                .stream()
                .map(post -> "<li>%s</li>".formatted(post.getTitle()))
                .collect(Collectors.joining()) + "</ul>";

        String body = """
                <h1>글 목록</h1>
                
                %s
                
                <a href="/posts/write">글쓰기</a>
                """.formatted(ul);

        return body;
    }

    @GetMapping("/write")
    @ResponseBody
    public String showWrite() {
        return getFormHtml("", "", "");
    }

    public record PostWriteForm(
            @NotBlank(message = "제목을 입력해주세요.")
            @Length(min = 5, message = "제목을 5자 이상 입력해주세요.")
            String title,
            @NotBlank(message = "내용을 입력해주세요.")
            @Length(min = 10, message = "내용을 10자 이상 입력해주세요.")
            String content
    ) {
    }

    @PostMapping("/write")
    @ResponseBody
    public String write(
            @ModelAttribute @Valid PostWriteForm form,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.joining("<br>"));

            return getFormHtml(
                    errorMessages,
                    form.title,
                    form.content
            );
        }

        return """
                <h1>글쓰기 완료</h1>
                
                <div>
                    <h2>%s</h2>
                    <p>%s</p>
                </div>
                """.formatted(form.title, form.content);
    }
}
