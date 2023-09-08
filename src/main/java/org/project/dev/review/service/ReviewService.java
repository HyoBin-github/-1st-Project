package org.project.dev.review.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.member.repository.MemberRepository;
import org.project.dev.product.entity.ProductEntity;
import org.project.dev.product.repository.ProductRepository;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.review.entity.ReviewEntity;
import org.project.dev.review.entity.ReviewFileEntity;
import org.project.dev.review.repository.ReviewFileRepository;
import org.project.dev.review.repository.ReviewRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    // BoardRepository
    private final ProductRepository productRepository;

    private final MemberRepository memberRepository;

    private final ReviewFileRepository reviewFileRepository;

    @Transactional
    public ReviewDto reviewAjaxCreate(ReviewDto reviewDto, String memberNickName) throws IOException {

//        Optional<MemberEntity> optionalMemberEntity =
//                Optional.ofNullable(memberRepository.findByMemberNickName(memberNickName));

        Optional<ProductEntity> optionalProductEntity
                = Optional.ofNullable(productRepository.findById(reviewDto.getProductId()).orElseThrow(IllegalArgumentException::new));

        if (optionalProductEntity.isPresent()) {
            if (reviewDto.getReviewFile().isEmpty()) {


                ProductEntity productEntity = new ProductEntity();
                productEntity.setId(reviewDto.getProductId());

                ReviewDto reviewDto1 = new ReviewDto();
                reviewDto1.setProductId(productEntity.getId());
                reviewDto1.setReview(reviewDto.getReview());
                reviewDto1.setReviewWriter(memberNickName);
                reviewDto1.setReviewFile(reviewDto.getReviewFile());
                reviewDto1.setProductEntity(productEntity);

                ReviewEntity reviewEntity = ReviewEntity.toReviewEntity(reviewDto1);

                Long reviewId = reviewRepository.save(reviewEntity).getId();

                Optional<ReviewEntity> optionalReviewEntity =
                        Optional.ofNullable(reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new));


                return ReviewDto.toReviewDto(optionalReviewEntity.get());


            } else {

                MultipartFile reviewFile = reviewDto.getReviewFile();
                String fileOldName = reviewFile.getOriginalFilename();
                UUID uuid = UUID.randomUUID();
                String fileNewName = uuid+"_"+fileOldName;
                String savePath = "C:/Users/bin77/Desktop/새 폴더/" + fileNewName;
                reviewFile.transferTo(new File(savePath));

                ProductEntity productEntity = new ProductEntity();
                productEntity.setId(reviewDto.getProductId());


                ReviewDto reviewDto1 = new ReviewDto();
                reviewDto1.setProductId(productEntity.getId());
                reviewDto1.setReview(reviewDto.getReview());
                reviewDto1.setReviewWriter(memberNickName);
                reviewDto1.setReviewFile(reviewDto.getReviewFile());
                reviewDto1.setReviewFileEntities(reviewDto.getReviewFileEntities());
                reviewDto1.setIsFile(reviewDto.getIsFile());
                reviewDto1.setProductEntity(productEntity);

                ReviewEntity reviewEntity = ReviewEntity.toReviewFileEntity(reviewDto1);

                Long reviewId = reviewRepository.save(reviewEntity).getId();

                Optional<ReviewEntity> optionalReviewEntity =
                        Optional.ofNullable(reviewRepository.findById(reviewId).orElseThrow(IllegalArgumentException::new));

                ReviewFileEntity reviewFileEntity
                        = ReviewFileEntity.toReviewFileEntity(reviewEntity,fileOldName,fileNewName);

                Long fileId = reviewFileRepository.save(reviewFileEntity).getId();

                reviewFileRepository.findById(fileId).orElseThrow(IllegalArgumentException::new);

                return ReviewDto.toReviewDto(optionalReviewEntity.get());

            }

        }
        return null;

    }

    @Transactional
    public int reviewDelete(Long id) {

        Optional<ReviewEntity> optionalReviewEntity
                = Optional.ofNullable(reviewRepository.findById(id).orElseThrow(IllegalArgumentException::new));

        System.out.println("=============" + optionalReviewEntity.get().getId() + "===================");
        if (optionalReviewEntity.isPresent()) {
            System.out.println("=============" + optionalReviewEntity.get().getId() + "===================");
            reviewRepository.deleteById(id);

            return 1;
        }
        return 0;


    }

    public List<ReviewDto> reviewList(Long id) {

        Optional<ProductEntity> optionalProductEntity = productRepository.findById(id);
        ProductEntity productEntity = optionalProductEntity.get();

        List<ReviewDto> reviewDtos = new ArrayList<>();
        List<ReviewEntity> reviewEntityList = reviewRepository.findAllByProductEntity(productEntity);

        for (ReviewEntity reviewEntity : reviewEntityList) {
            ReviewDto reviewDto = ReviewDto.toReviewDto(reviewEntity);
            reviewDtos.add(reviewDto);
        }
        return reviewDtos;
    }
}
