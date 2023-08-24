package org.project.dev.product.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.dto.ProductImgDTO;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.entity.ProductImgEntity;
import org.project.dev.product.repository.ProductImgRepository;
import org.project.dev.product.repository.ProductRepository;
import org.project.dev.utils.FileStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

    // 이미지 정보를 DB에 업로드
    public void saveProductImages(ProductEntity savedProductEntity, List<MultipartFile> files) throws IOException {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile multipartFile : files) {
                if (!multipartFile.isEmpty()) {
                    // storeFile 메서드를 사용하여 파일을 저장하고, 저장된 파일의 경로를 savedPath에 저장.
                    // 첫 번째 매개변수(fileType)로 "product"는 해당 파일이 상품 관련 파일임을 전달.
                    String savedPath = fileStorageService.storeFile("product", multipartFile);

                    // Lombok의 builder를 사용하여 ProductImgEntity 객체 생성
                    ProductImgEntity productImgEntity = ProductImgEntity.builder()
                            .productEntity(savedProductEntity)
                            .productImgOriginalName(multipartFile.getOriginalFilename())
                            .productImgSavedName(new File(savedPath).getName())
                            .productImgSavedPath(savedPath)
                            .build();

                    productImgRepository.save(productImgEntity);
                }
            }
        }
    }

    // 이미지 정보를 가져오기. (product_id 기준)
    public List<ProductImgDTO> getProductImagesByProductId(Long productId) {
        List<ProductImgEntity> imgEntities = productImgRepository.findByProductId(productId);
        return imgEntities.stream()
                .map(ProductImgDTO::toDTO)
                .collect(Collectors.toList());
    }

    // 메인 이미지 가져오기.
    public List<ProductImgDTO> getMainProductImages(List<ProductDTO> products) {
        return products.stream()
                .map(product -> getProductImagesByProductId(product.getId()))
                .map(images -> {
                    return images.stream()
                            .filter(img -> img.getIsProductImgMain())
                            .findFirst()
                            .orElse(images.isEmpty() ? null : images.get(0));
                })
                .collect(Collectors.toList());
    }

}
