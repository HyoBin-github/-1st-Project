package org.project.dev.product.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.product.dto.ProductDTO;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.repository.ProductRepository;
import org.project.dev.product.repository.ProductSpecification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. CRUD 처리를 위한 로직들이 모인 곳입니다.
     3. x
     4. x
     */


    private final ProductRepository productRepository;


    // WRITE (CREATE)
    @Transactional
    public ProductEntity productWriteDetail(ProductDTO productDTO){
        productDTO.setProductHits(0); // productHits 초기화
        ProductEntity productEntity = ProductEntity.toEntity(productDTO);
        return productRepository.save(productEntity);
    }

    // LIST with search & pagination (READ)
    // 검색어 없이 리스트 출력
    public Page<ProductDTO> productNoSearchList(Pageable pageable) {
        Specification<ProductEntity> entitySpecification = Specification.where(ProductSpecification.isDisplayTrue());
        Page<ProductEntity> productEntities = productRepository.findAll(entitySpecification,pageable);
        return productEntities.map(ProductDTO::toDTO);
    }

    // 검색어를 통한 리스트 출력
    public Page<ProductDTO> productSearchList(String searchType, String searchKeyword, Pageable pageable) {
        // DB의 productDisplay 값이 true 인 경우에만 보이도록 처리. (ADMIN/SELLER의 경우에는 false여도 보이도록 처리)
        Specification<ProductEntity> entitySpecification = Specification.where(ProductSpecification.isDisplayTrue());

        // 검색타입 추가 시, 이 곳에 위치하게 됩니다.
        switch (searchType) {
            case "productNameContains":
                entitySpecification = entitySpecification
                        .and(ProductSpecification.productNameContains(searchKeyword));
                break;
            case "productDescriptionContains":
                entitySpecification = entitySpecification
                        .and(ProductSpecification.productDescriptionContains(searchKeyword));
                break;
        }

        Page<ProductEntity> productEntities = productRepository.findAll(entitySpecification, pageable);
        return productEntities.map(ProductDTO::toDTO); // Entity를 DTO로 변환
    }

    // 페이징처리 : 시작 페이지 계산 로직
    public static int calculateStartPage(int nowPage, int totalPage) {
        int startPage = Math.max(nowPage - 4, 1);
        if (totalPage - startPage < 8) {
            startPage = Math.max(totalPage - 8, 1);
        }
        return startPage;
    }

    // 페이징처리 : 마지막 페이지 계산 로직
    public static int calculateEndPage(int nowPage, int totalPage) {
        int endPage = Math.min(nowPage + 4, totalPage);
        if (endPage - calculateStartPage(nowPage, totalPage) < 8) {
            endPage = Math.min(calculateStartPage(nowPage, totalPage) + 8, totalPage);
        }
        return endPage;
    }


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



    // DETAIL (SELECT) & UPDATE (UPDATE) & UPDATE PROCESS (UPDATE)
    @Transactional(readOnly = true)
    public ProductDTO productViewDetail(Long id) {
        // 게시물의 ID를 이용하여 해당 게시물을 데이터베이스에서 찾음
        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        // 만약 해당 ID에 해당하는 게시물이 존재한다면,
        if(optionalProductEntity.isPresent()){
            // Optional 객체에서 실제 ProductEntity 객체를 가져옴
            ProductEntity productEntity = optionalProductEntity.get();
            // 가져온 ProductEntity 객체를 ProductDTO 객체로 변환
            ProductDTO productDTO = ProductDTO.toDTO(productEntity);
            // 변환된 ProductDTO 객체를 반환
            return productDTO;
        }else{
            // 해당 ID에 해당하는 게시물이 존재하지 않을 경우 null 반환
            return null;
        }
    }

    // UPDATE PROCESS (UPDATE)
    @Transactional
    public ProductDTO productUpdateDetail(ProductDTO productDTO) {
        ProductEntity productEntity = ProductEntity.toEntity(productDTO);
        productRepository.save(productEntity);
        return productViewDetail(productDTO.getId());
    }

    // DELETE (DELETE)
    // 이름은 delete이지만, 실제 로직은 update.
    // productDisplay 값을 false로 바꿔, 사용자에게 보이지 않도록 처리합니다.
    @Transactional
    public void delete(Long id) {
        productRepository.softDelete(id);
    }


}