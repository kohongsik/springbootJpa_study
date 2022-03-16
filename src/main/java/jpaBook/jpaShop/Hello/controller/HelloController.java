package jpaBook.jpaShop.Hello.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    @GetMapping("/hello")
    // Model : spring UI 에 model이라는 뷰에 데이터를 넘길 수 있음.
    public String hello(Model model) {
        model.addAttribute("data", "hello");
        return "Hello"; // 받는 이름 <- html 경로
    }
}
