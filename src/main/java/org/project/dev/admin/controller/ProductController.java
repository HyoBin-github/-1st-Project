package org.project.dev.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/product")
public class ProductController {

    /*
        TODO
        판매자가 자신의 상품 리스트를 조회 할때 쓰입니다
        여기서는 상품 수정 삭제 가능합니다.
         */
    @GetMapping
    public String getProductList() {

        return "/admin/list";
    }

    @GetMapping
    public String getProductDetail() {

        return "/admin/detail";
    }


}
