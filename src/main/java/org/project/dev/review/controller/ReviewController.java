package org.project.dev.review.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.config.MyUserDetails;
import org.project.dev.review.dto.ReviewDto;
import org.project.dev.review.service.ReviewService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/review")
@RestController
public class ReviewController {

    private final ReviewService reviewService;


    @PostMapping("/ajaxWrite")
    public @ResponseBody ReviewDto ajaxWrite(@ModelAttribute ReviewDto reviewDto/*, @AuthenticationPrincipal MyUserDetails myUserDetails*/){

        String memberNickName = "m1"; /*myUserDetails.getUsername();*/
        ReviewDto reviewDto1 = reviewService.reviewAjaxCreate(reviewDto, memberNickName);

        return reviewDto1;
    }

    @PostMapping("/delete/{id}")
    public @ResponseBody ReviewDto reviewDelete(@RequestParam("id") Long id){

        reviewService.reviewDelete(id);


        return new ReviewDto();
    }
}
