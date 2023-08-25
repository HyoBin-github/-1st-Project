package org.project.dev.review.repository;

import org.project.dev.product.entity.ProductEntity;
import org.project.dev.review.entity.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity,Long> {

    List<ReviewEntity> findAllByProductEntity(ProductEntity productEntity);
}
