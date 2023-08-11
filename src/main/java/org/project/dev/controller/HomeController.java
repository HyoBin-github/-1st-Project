package org.project.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class HomeController {


    /*
    TODO : 만든 사람 (이메일 주소나 영문명)
       메소드 설명 ~
        필수 데이터 ~
     */
    @GetMapping
    public String index(){

        return "index";
    }
}
