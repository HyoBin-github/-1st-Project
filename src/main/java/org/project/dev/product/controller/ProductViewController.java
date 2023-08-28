package org.project.dev.product.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.service.ProductService;
import org.project.dev.product.service.ProductViewService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/productView")
public class ProductViewController {

    private final ProductViewService productViewService;


    // Cursor-Based List
    @GetMapping("/cursorBasedList")
    public String cursorBasedList(@RequestParam(required = false) Long lastId, Model model) {
        int limit = 10; // 페이지당 표시할 개수
        List<ProductDTO> productDTOS = productViewService.productCursorBasedList(lastId, limit);
        model.addAttribute("products", productDTOS);

        if (!productDTOS.isEmpty()) {
            model.addAttribute("nextCursor", productDTOS.get(productDTOS.size() - 1).getId());
        }

        return "/product/cursorBasedList";
    }

    // Detail
//    @GetMapping





}
