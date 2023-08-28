package org.project.dev.product.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.repository.ProductRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductViewService {

    private final ProductRepository productRepository;

    // Cursor-Based List
    public List<ProductDTO> productCursorBasedList(Long lastId, int limit) {
        List<ProductEntity> productEntities;
        Pageable pageable = PageRequest.of(0, limit); // 첫 페이지에서 'limit' 개의 결과 가져오기

        if (lastId == null) {
            productEntities = productRepository.findProductsByOrderByIdDesc(pageable);
        } else {
            productEntities = productRepository.findProductsByIdLessThanOrderByIdDesc(lastId, pageable);
        }

        return productEntities.stream().map(ProductDTO::toDTO).collect(Collectors.toList());
    }

}
