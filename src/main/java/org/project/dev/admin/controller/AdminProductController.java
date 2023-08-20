package org.project.dev.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product1")
public class AdminProductController {

    /*
        TODO
        판매자가 자신의 상품 리스트를 조회 할때 쓰입니다
        여기서는 상품 수정 삭제 가능합니다.
         */
    @GetMapping("/list")
    public String getProductList() {

        return "/admin/list";
    }

    /*
    TODO
    상품 1개의 디테일한 정보를 표시합니다
    가격 활성화 기타 등등 정보
     */
    @GetMapping("/detail")
    public String getProductDetail() {

        return "/admin/detail";
    }

    /*
    TODO
    상품 업데이트 처리 메소드 생성 예정
     */


}
