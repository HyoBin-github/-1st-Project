package org.project.dev.review.entity;

import lombok.*;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Getter
@Setter
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

//    private Long likeCount;

//    private Long reviewStar;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "board_id")
    private ProductEntity productEntity;

    @Column(nullable = false, length = 1)
    private int isFile;

    @OneToMany(mappedBy = "reviewEntity", cascade = CascadeType.REMOVE)
    private List<ReviewFileEntity> reviewFileEntities = new ArrayList<>();


    public static ReviewEntity toReviewEntity(ReviewDto reviewDto1) {

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview(reviewDto1.getReview());
        reviewEntity.setReviewWriter(reviewDto1.getReviewWriter());
        reviewEntity.setProductId(reviewDto1.getProductId());
        reviewEntity.setIsFile(0);
        reviewEntity.setProductEntity(reviewDto1.getProductEntity());

        return reviewEntity;


    }

    public static ReviewEntity toReviewFileEntity(ReviewDto reviewDto1) {

        ReviewEntity reviewEntity = new ReviewEntity();
        reviewEntity.setReview(reviewDto1.getReview());
        reviewEntity.setReviewWriter(reviewDto1.getReviewWriter());
        reviewEntity.setProductId(reviewDto1.getProductId());
        reviewEntity.setIsFile(1);
        reviewEntity.setProductEntity(reviewDto1.getProductEntity());

        return reviewEntity;
    }
}
