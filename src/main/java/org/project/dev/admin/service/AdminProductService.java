package org.project.dev.admin.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.admin.dto.AdminProductDto;
import org.project.dev.admin.entity.AdminProductEntity;
import org.project.dev.admin.repository.AdminProductRepository;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.entity.ProductEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AdminProductService {

    private final AdminProductRepository adminProductRepository;


    /*
    TODO
    검색조건 달아야함
    그냥 다 땡겨옴 null 처리 안함
     */
    public List<ProductDTO> getProductList(int memberId) {
        List<ProductDTO> productDtoList = new ArrayList<>();
        List<ProductEntity> productEntityList = adminProductRepository.getMemberIdProductList(memberId);

        if (productEntityList.isEmpty()) {
            throw new IllegalArgumentException("가져올 상품이 없습니다");
        }

        for (ProductEntity productEntity : productEntityList) {
            productDtoList.add(ProductDTO.toDTO(productEntity));

        }
        return productDtoList;
    }


    /*
    TODO
    단일조회
    */
    public ProductDTO getProductDetail(Long productId) {
        Optional<ProductEntity> productEntity = Optional.ofNullable(adminProductRepository.findById(productId).orElseThrow(() -> {
            throw new IllegalArgumentException("가져올 항목이 없습니다");
        }));

        return ProductDTO.toDTO(productEntity.get());


    }
}
