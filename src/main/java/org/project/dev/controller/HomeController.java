package org.project.dev.controller;


import lombok.RequiredArgsConstructor;
import org.project.dev.config.member.MyUserDetails;
import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.service.ImageService;
import org.project.dev.member.service.ImageServiceImpl;
import org.project.dev.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/*

 */

@Controller
@RequiredArgsConstructor
//@RequestMapping("/")
public class HomeController {

    /*
    Todo
     1. 코드 작성자 (이메일 기재)
     2. 간략한 기능 설명
     3. 필수 데이터
     4. 기타
     */


     /*
    Todo
     1. songwc3@gmail.com
     2. 회원가입, 로그인 View
     3. 없음
     4. 없음
     */

    @GetMapping({"","/index"})
    public String index() {
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

    /*
    Todo
     1. songwc3@gmail.com
     2. 회원가입, 로그인 View
     3. 없음
     4. 없음
     */
    @GetMapping("/join")
    public String join(){
        return "join";
    }

    // 로그인 View(일반회원, 간편회원 포함)
    @GetMapping("/login")
    public String getLogin(){
        return "login";
    }


}
