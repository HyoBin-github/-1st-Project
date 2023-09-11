package org.project.dev.admin.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.admin.dto.AdminProductDto;
import org.project.dev.admin.service.AdminProductService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/*
TODO
상품 CRUD 에 관련된 api

 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class AdminProductController {

    private final AdminProductService productService;

    /*
        TODO
        조건 검색 들어가야하고 페이징 들어가야하고 일단은 모두 다 보이게만!
    */
    @GetMapping("/list")
    public Map<String,Object> getProductList(){
        Map<String,Object> response = new HashMap<String,Object>();
        List<AdminProductDto> adminProductDtoList = productService.getProductList();
        response.put("productList",adminProductDtoList);
        return response;
    }

}
