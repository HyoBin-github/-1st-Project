package org.project.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*

 */

@Controller
@RequestMapping("/login")
public class LoginController {


    /*
    Todo
     1. catfather@49gamil.com
     2. 이 사이트를 접속하면 제일 처음 요청이 들어오는 컨트롤러입니다
     3. 없음
     4. 없음
     */
    @GetMapping("")
    public String login(){
        return "/login/login";
    }
}
