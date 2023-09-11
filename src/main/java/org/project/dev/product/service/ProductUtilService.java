package org.project.dev.product.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.dto.ProductImgDTO;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.entity.ProductImgEntity;
import org.project.dev.product.repository.ProductImgRepository;
import org.project.dev.product.repository.ProductImgSpecification;
import org.project.dev.product.repository.ProductRepository;
import org.project.dev.product.repository.ProductSpecification;
import org.project.dev.utils.FileStorageService;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductUtilService {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. CRUD 처리 외의 로직들이 모인 곳입니다.
     3. x
     4. x
     */

    private final ProductRepository productRepository;
    private final ProductImgRepository productImgRepository;
    private final FileStorageService fileStorageService;

    // HITS, DETAIL (SELECT)
    @Transactional
    public void updateHits(Long id) {
        // productRepository의 updateHits 메소드를 호출
        productRepository.updateHits(id);
    }

    public void saveProductImages(ProductEntity savedProductEntity,
                                  List<MultipartFile> productImages) throws IOException {

        if (productImages != null && !productImages.isEmpty()) {
            for (int i = 0; i < productImages.size(); i++) {
                MultipartFile multipartFile = productImages.get(i);
                if (!multipartFile.isEmpty()) {
                    String savedPath = fileStorageService.storeFile("product", multipartFile);

                    ProductImgEntity productImgEntity = ProductImgEntity.builder()
                            .productEntity(savedProductEntity)
                            .productImgOriginalName(multipartFile.getOriginalFilename())
                            .productImgSavedName(new File(savedPath).getName())
                            .productImgSavedPath(savedPath)
                            .productImgOrder(i)
                            .isProductImgDisplayed(true)
                            .build();

                    productImgRepository.save(productImgEntity);
                }
            }
        }
    }




    // 이미지 정보를 가져오기. (product_id 기준)
    public List<ProductImgDTO> getProductImagesByProductId(Long productId) {
        // DB의 isProductImgDisplay 값이 true 인 경우 & ProductId로 필터링.
        Specification<ProductImgEntity> combinedSpec = Specification
                .where(ProductImgSpecification.isDisplayTrue())
                .and(ProductImgSpecification.byProductId(productId));
        Sort sort = Sort.by(Sort.Order.asc("productImgOrder"));  // 오름차순 정렬
        List<ProductImgEntity> imgEntities = productImgRepository.findAll(combinedSpec, sort);

        return imgEntities.stream()
                .map(ProductImgDTO::toDTO)
                .collect(Collectors.toList());
    }

    // 메인 이미지 가져오기.
    public List<ProductImgDTO> getMainProductImage(List<ProductDTO> products) {
        return products.stream()
                .map(product -> getProductImagesByProductId(product.getId()))
                .map(images -> {
                    return images.stream()
                            .filter(img -> img.getProductImgOrder() == 0)
                            .findFirst()
                            .orElse(images.isEmpty() ? null : images.get(0));
                })
                .collect(Collectors.toList());
    }

















}
