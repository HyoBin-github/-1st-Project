package org.project.dev.review.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.review.entity.ReviewEntity;
import org.project.dev.review.service.ReviewService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/ajaxWrite")
    public @ResponseBody ReviewDto ajaxWrite(@ModelAttribute ReviewDto reviewDto
            /*, @AuthenticationPrincipal MyUserDetails myUserDetails*/) throws IOException {

        System.out.println(reviewDto.getReview()+"<<<< Review");
        System.out.println("review>>" + reviewDto.getReview());
        String memberNickName = "m1"; /*myUserDetails.getUsername();*/
        ReviewDto reviewDto1 = reviewService.reviewAjaxCreate(reviewDto, memberNickName);

        return reviewDto1;
    }

    @PostMapping("/delete/{id}")
    public int reviewDelete(@PathVariable("id") Long id){

        System.out.println(id + "<<id");
        int rs = reviewService.reviewDelete(id);



        return rs;
    }

    @GetMapping("/reviewList/{id}")
    public @ResponseBody List<ReviewDto> reviewDto(@ModelAttribute ReviewDto reviewDto, @PathVariable Long id){

        List<ReviewDto> reviewDtos = reviewService.reviewList(id);

        return reviewDtos;
    }

}
