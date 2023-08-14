package org.project.dev.controller;

import org.project.dev.dto.MemberDto;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/*

 */

@Controller
@RequestMapping("/")
public class HomeController {


    /*
    Todo
     1. 코드 작성자 (이메일 기재)
     2. 간략한 기능 설명
     3. 필수 데이터
     4. 기타
     */
    @GetMapping
    @ResponseBody
    public String index(Model model){

        return "/index";
    }
}
