package com.example.tem;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.Length;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
    private List<Post> posts = new ArrayList<>();

    @GetMapping("/list")
    public String showList(Model model) {
        model.addAttribute("posts", posts);

        return "post_list";
    }

    @GetMapping("/write")
    public String showWrite(
            @ModelAttribute("form") PostWriteForm form
    ) {
        return "post_write";
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
    public String write(
            @ModelAttribute("form") @Valid PostWriteForm form,
            BindingResult bindingResult,
            Model model
    ) {
        if (bindingResult.hasErrors()) {
            String errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.joining("<br>"));

            model.addAttribute("errorMessage", errorMessages);

            return "post_write";
        }

        posts.add(
                Post.builder()
                        .title(form.title)
                        .content(form.content)
                        .build()
        );

        return "redirect:/posts/list";
    }
}
