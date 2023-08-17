package org.project.dev.notice.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.InquiryDto;
import org.project.dev.notice.service.InquiryService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    private final InquiryService inquiryService;

    /*
   Todo
    1. rladpwls1843@gamil.com
    2. 문의사항 쓰기페이지로 이동
    3.
    4.
    */
    @GetMapping("/write")
    public String getInquiryWrite(InquiryDto inquiryDto){
        return "inquiry/write";
    }
    @PostMapping("/write")
    public String postInquiryWrite(@Validated InquiryDto inquiryDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "inquiry/write";
        }
        int rs = inquiryService.InquiryInsert(inquiryDto);
        if(rs==1){
            return "redirect:/inquiry/List";
        }
        return "index";
    }
    /*
   Todo
    1. rladpwls1843@gamil.com
    2. 문의사항 목록페이지로 이동
    3.
    4.
    */
    @GetMapping("/List")
    public String getInquiryList(@PageableDefault(page=0, size=5, sort = "id",
            direction = Sort.Direction.DESC)Pageable pageable, Model model){

        Page<InquiryDto> pagingInquiryList = inquiryService.InquiryList(pageable);

        Long totalCount = pagingInquiryList.getTotalElements();
        int totalPage = pagingInquiryList.getTotalPages();
        int pageSize = pagingInquiryList.getSize();
        int nowPage = pagingInquiryList.getNumber();
        int blockNum = 5;

        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum) + 1 <= totalPage ?
                (Math.floor(nowPage/blockNum)*blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        if(!pagingInquiryList.isEmpty()){
            model.addAttribute("inquiryList", pagingInquiryList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            return "inquiry/List";
        }
        System.out.println("조회할 문의사항이 없다.");

        return "inquiry/List";
    }
}
