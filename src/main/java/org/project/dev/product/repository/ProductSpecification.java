package org.project.dev.product.repository;

import org.project.dev.product.entity.ProductEntity;
import org.springframework.data.jpa.domain.Specification;

public class ProductSpecification {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. 상품 관련 Specification 클래스 입니다. Specification 쿼리들이 모여있는 곳 입니다.
        1) 삭제된 글(productDisplay=false)은 보이지 않도록 하는 쿼리.
        2) 검색타입을 결정하기 위한 쿼리.
     3. x
     4. x
     */

    // DB의 productDisplay 값이 true 일 때만 반환하는 쿼리
    public static Specification<ProductEntity> isDisplayTrue(){
        return (root, query, criteriaBuilder) -> criteriaBuilder.equal(root.get("isProductDisplayed"),true);
    }

    // DB에 저장된 productName의 값 중, searchKeyword에 입력된 값이 포함되어 있는 경우에만 반환하는 쿼리
    public static Specification<ProductEntity> productNameContains(String searchKeyword){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("productName"), "%" + searchKeyword + "%");
    }

    // DB에 저장된 productDescription의 값 중, searchKeyword에 입력된 값이 포함되어 있는 경우에만 반환하는 쿼리
    public static Specification<ProductEntity> productDescriptionContains(String searchKeyword){
        return (root, query, criteriaBuilder) -> criteriaBuilder.like(root.get("productDescription"), "%" + searchKeyword + "%");
    }


}
