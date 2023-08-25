package org.project.dev.review.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.repository.MemberRepository;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.repository.ProductRepository;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.review.entity.ReviewEntity;
import org.project.dev.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // BoardRepository
    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    public ReviewDto reviewAjaxCreate(ReviewDto reviewDto, String memberNickName) {

//        Optional<MemberEntity> optionalMemberEntity =
//                Optional.ofNullable(memberRepository.findByMemberNickName(memberNickName));

        Optional<ProductEntity> optionalProductEntity
                = Optional.ofNullable(productRepository.findById(reviewDto.getProductId()).orElseThrow(IllegalArgumentException::new));


        if (optionalProductEntity.isPresent()) {
            ProductEntity productEntity = new ProductEntity();
            productEntity.setId(reviewDto.getProductId());

            ReviewDto reviewDto1 = new ReviewDto();
            reviewDto1.setProductId(productEntity.getId());
            reviewDto1.setReview(reviewDto.getReview());
            reviewDto1.setReviewWriter(memberNickName);
            reviewDto1.setProductEntity(productEntity);

            ReviewEntity reviewEntity = ReviewEntity.toReviewEntity(reviewDto1);

            Long reviewId = reviewRepository.save(reviewEntity).getId();

            Optional<ReviewEntity> optionalReviewEntity =
                    Optional.ofNullable(reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new));


            return ReviewDto.toReviewDto(optionalReviewEntity.get());
        }
        return null;
    }

}
