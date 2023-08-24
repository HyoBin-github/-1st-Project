package org.project.dev.admin.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.admin.dto.AdminProductDto;
import org.project.dev.admin.entity.AdminProductEntity;
import org.project.dev.admin.repository.AdminProductRepository;
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
    public List<AdminProductDto> getProductList() {
        List<AdminProductDto> productDtoList = new ArrayList<>();
        List<AdminProductEntity> adminProductEntityList = adminProductRepository.findAll();

        for (AdminProductEntity adminProductEntity : adminProductEntityList) {
            productDtoList.add(AdminProductDto.toDto(adminProductEntity));
        }
        return productDtoList;
    }


    /*
    TODO
    단일조회
    */
    public AdminProductDto getProductDetail(Long productId) {
        Optional<AdminProductEntity> adminProductEntity = Optional.ofNullable(adminProductRepository.findById(productId).orElseThrow(() -> {
            throw new IllegalArgumentException("찾는 해당 아이디가 없습니다");
        }));

        return AdminProductDto.toDto(adminProductEntity.get());


    }

    /*

    TODO
    product 생성 간단함 
     */
    public String productInsert(AdminProductDto productDto) {

        AdminProductEntity productEntity = AdminProductEntity.toEntity(productDto);
        Long result = adminProductRepository.save(productEntity).getId();
        if (result == null){
            throw new RuntimeException("product생성중 id 가 null로 반환됨!");
        }
        return "ok";
    }

    
    /*
    TODO
    상품 수정
     */
    public String productUpdate(AdminProductDto productDto) {

        AdminProductEntity productEntity = AdminProductEntity.toEntity(productDto);
        Long result = adminProductRepository.save(productEntity).getId();
        if (result == null){
            throw new RuntimeException("product업데이트중 id 가 null로 반환됨!");
        }
        return "ok";
    }
}
