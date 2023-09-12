package org.project.dev.product.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductBrandDTO;
import org.project.dev.product.entity.ProductBrandEntity;
import org.project.dev.product.repository.ProductBrandRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
public class AutoCompleteController {

    private final ProductBrandRepository productBrandRepository;

    @GetMapping("/search")
    public List<ProductBrandDTO> search(@RequestParam("keyword") String keyword){
        try {
            List<ProductBrandEntity> entities =
                    productBrandRepository.findByProductBrandNameContaining(keyword);
            List<ProductBrandDTO> dtos = entities.stream()
                    .map(ProductBrandDTO::toDTO)
                    .collect(Collectors.toList());
            return dtos;
        } catch (Exception e) {
            // 예외 처리
            return Collections.emptyList();
        }
    }


}
