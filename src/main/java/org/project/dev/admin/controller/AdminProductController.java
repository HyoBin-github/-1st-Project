package org.project.dev.admin.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.admin.service.AdminProductService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin/product")
@RequiredArgsConstructor
public class AdminProductController {

    private final AdminProductService adminProductService;

    @GetMapping("{id}/list")
    public String getAdminProductList(@PathVariable("id") Long memberId) {

        adminProductService.getProductList(memberId);

        return "admin/index";

    }

}
