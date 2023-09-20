package org.project.dev.product.repository;

import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.entity.ProductImgEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductImgRepository extends JpaRepository<ProductImgEntity, Long>, JpaSpecificationExecutor<ProductImgEntity> {

    /*
    Todo
     1. code.aiaru@gmail.com
     2. 상품 이미지 관련 Repository 입니다.
        JPQL 쿼리문은 이 곳에 위치하게 됩니다.
     3. x
     4. x
     */

    @Query("SELECT img FROM ProductImgEntity img WHERE img.productEntity.id = ?1")
    List<ProductImgEntity> findByProductId(Long productId);

    List<ProductImgEntity> findByProductEntity(ProductEntity productEntity);

}

