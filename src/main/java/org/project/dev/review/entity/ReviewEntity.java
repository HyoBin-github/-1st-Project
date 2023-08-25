package org.project.dev.review.entity;

import lombok.*;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "product_review")
public class ReviewEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    @Column(nullable = false, name = "review_content")
    private String review;

    @Column(name = "product_review_writer", nullable = false)
    private String reviewWriter;

    private Long productId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private ProductEntity productEntity;

    public static ReviewEntity toReviewEntity(ReviewDto reviewDto1) {

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview(reviewDto1.getReview());
        reviewEntity.setReviewWriter(reviewDto1.getReviewWriter());
        reviewEntity.setProductId(reviewDto1.getProductId());
        reviewEntity.setProductEntity(reviewDto1.getProductEntity());

        return reviewEntity;


    }
}
