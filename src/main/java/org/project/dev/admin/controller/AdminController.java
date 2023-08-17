package org.project.dev.admin.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {


    /*
    TODO
    판매자 페이지(admin,seller만 접근 허용)
     member가 접근하는 페이지 로그인에서는 링크를 타고 들어와서 여기서 admin seller 로그인
     admin의 메인페이지는 무조건 로그인 화면 로그인이 되어야 들어올수 있음
     */
    @GetMapping("/login")
    public String index(){

        return "/admin/login";
    }

}
