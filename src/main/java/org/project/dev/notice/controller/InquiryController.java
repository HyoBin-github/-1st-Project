package org.project.dev.notice.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.InquiryDto;
import org.project.dev.notice.service.InquiryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

@Controller
@RequestMapping("/inquiry")
@RequiredArgsConstructor
public class InquiryController {

    @Autowired
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
    public String postInquiryWrite(@Validated InquiryDto inquiryDto, BindingResult bindingResult, Model model) throws IOException {
        if(bindingResult.hasErrors()){
            return "inquiry/write";
        }
        int rs = inquiryService.InquiryInsert(inquiryDto);
        if(rs==1){
            return "redirect:/inquiry/list";
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
    @GetMapping("/list")
    public String getInquiryList(@PageableDefault(page=0, size=5, sort = "inqId",
            direction = Sort.Direction.DESC) Pageable pageable, Model model){

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
            return "inquiry/list";
        }
        System.out.println("조회할 문의사항이 없다.");

        return "inquiry/list";
    }
    /*
     Todo
      1. rladpwls1843@gamil.com
      2. 문의사항 검색 페이지로 이동
      3.
      4.
      */
    @GetMapping("/searchList")
    public String getInquirySearchList(Model model){
        List<InquiryDto> inquiryDtoList = inquiryService.findAllList();

        if(!inquiryDtoList.isEmpty()){
            model.addAttribute("inquiryList", inquiryDtoList);
            return "inquiry/searchlist";
        }
        return "redirect:inquiry/list";
    }
    @GetMapping("/search")
    public String getInquirySearch(
            @RequestParam(value = "select", required = false) String inquirySelect,
            @RequestParam(value = "search", required = false) String inquirySearch,
            Model model){

        List<InquiryDto> inquiryDtoList = inquiryService.InquiryListSearch(inquirySelect,inquirySearch);
        // thymeleaf-paging에서 컨트롤러에서 뷰로 넘길 때 list형태로 model에 담아 오류 발생

        if(!inquiryDtoList.isEmpty()){
            model.addAttribute("inquiryList", inquiryDtoList);
            return "inquiry/searchlist";
        }
        return "redirect:inquiry/list";
    }

    /*
           Todo
            1. rladpwls1843@gamil.com
            2. 문의사항 상세페이지로 이동 -> 해당 ID에 맞는
            3.
            4.
            */
    @GetMapping("/detail/{id}")
    public String getInquiryDetail(@PathVariable("id") Long id, Model model){
        InquiryDto inquiryDto = inquiryService.InquiryDetail(id);

        if(inquiryDto != null){
            model.addAttribute("inquiryDto", inquiryDto);
//            List<ReplyDto> replyDtoList = replyService.relpyList(inquiryDto.getNotId());
//            model.addAttribute("replyDtoList", replyDtoList);
            return "inquiry/detail";
        }
        return "redirect:/inquiry/list";
    }

    /*
    TODO
    문의사항 수정하기 페이지로 이동
    */
    @GetMapping("/update/{id}")
    public String getInquiryUpdate(@PathVariable("id") Long id, Model model){

        InquiryDto inquiryDto = inquiryService.InquiryUpdate(id);

        if(inquiryDto != null){
            model.addAttribute("inquiryDto",inquiryDto);
            return "inquiry/update";
        }
        return "redirect:/inquiry/list";
    }
    @PostMapping("/update/{id}")
    public String postInquiryUpdate(@PathVariable("id") Long id, InquiryDto inquiryDto){
        int rs = inquiryService.InquiryUpdateOk(inquiryDto, id);

        if(rs == 1){
            System.out.println("수정 성공");
        }else{
            System.out.println("수정 실패");
        }
        return "redirect:/inquiry/detail/"+id;
    }
    /*
    TODO
    문의사항 삭제하기 페이지로 이동
    */
    @GetMapping("/delete/{id}")
    public String getInquiryDelete(@PathVariable("id") Long id){
        int rs = inquiryService.InquiryDelete(id);
        if(rs == 1){
            System.out.println("문의사항 삭제");
        }else{
            System.out.println("문의사항 삭제 실패");
        }
        return "redirect:/inquiry/";
    }
}
