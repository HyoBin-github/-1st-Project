package org.project.dev.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/*

 */

@Controller
@RequestMapping("/product")
public class ProductController {

    /*
    TODO
    product 상품 페이지에서 첫 요첫 받는 메소드
     */
    @GetMapping("/list")
    public String getProductList() {
        return "product/product";
    }
}
