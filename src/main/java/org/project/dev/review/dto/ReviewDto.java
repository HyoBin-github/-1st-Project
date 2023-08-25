package org.project.dev.review.dto;

import lombok.*;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.review.entity.ReviewEntity;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ReviewDto {

    private Long id;

    private String review;

    private String reviewWriter;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    // 덧글 작성시 board_id;
    private Long productId;

    private ProductEntity productEntity;




    public static ReviewDto toReviewDto(ReviewEntity reviewEntity) {

        ReviewDto reviewDto = new ReviewDto();
        reviewDto.setId(reviewEntity.getId());
        reviewDto.setReview(reviewEntity.getReview());
        reviewDto.setReviewWriter(reviewEntity.getReviewWriter());
        reviewDto.setProductId(reviewEntity.getProductEntity().getId());
        reviewDto.setCreateTime(reviewEntity.getCreateTime());
        reviewDto.setUpdateTime(reviewEntity.getUpdateTime());


        return reviewDto;

    }
}
