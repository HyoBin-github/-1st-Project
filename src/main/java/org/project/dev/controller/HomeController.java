package org.project.dev.controller;


import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;


/*

 */

@Controller
//@RequestMapping("/")
public class HomeController {


    /*
    Todo
     1. 코드 작성자 (이메일 기재)
     2. 간략한 기능 설명
     3. 필수 데이터
     4. 기타
     */

    @GetMapping({"","/index"})
    public String index(){
        return "index";
    }

    /*
    Todo
     1. catfather@49gamil.com
     2. 이 사이트를 접속하면 제일 처음 요청이 들어오는 컨트롤러입니다
     3. 없음
     4. 없음
     */
    @GetMapping("/kream")
    public String kream(){
        return "kream";
    }
}
