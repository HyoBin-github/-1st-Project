package org.project.dev.notice.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.NoticeDto;
import org.project.dev.notice.service.NoticeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/notice")
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeService noticeService;

    /*
    Todo
     1. rladpwls1843@gamil.com
     2. 공지사항 쓰기페이지로 이동
     3.
     4.
     */
    @GetMapping("/write")
    public String getNoticeWrite(NoticeDto noticeDto){
        return "notice/write";
    }
    @PostMapping("/write")
    public String postNoticeWrite(@Validated NoticeDto noticeDto, BindingResult bindingResult, Model model){
        if(bindingResult.hasErrors()){
            return "notice/write";
        }
        int rs = noticeService.NoticeInsert(noticeDto);
        if(rs==1){
            return "redirect:/notice/List";
        }
        return "index";
    }

    /*noticeList*/
//    @GetMapping("/List")
//    public String getNoticeList(Model model){
//        List<NoticeDto>  noticeDtoList = noticeService.findAllNoticeList();
//
//        if(!noticeDtoList.isEmpty()){
//            model.addAttribute("noticeDtoList", noticeDtoList);
//            return "notice/List";
//        }
//        System.out.println("조회할 공지사항이 없습니다.");
//        return "redirect:/index";
//    }
 /*
    Todo
     1. rladpwls1843@gamil.com
     2. 공지사항 목록 페이지로 이동
     3.
     4.
     */
    @GetMapping("/List")
    public String getNoticeList(@PageableDefault(page = 0, size = 3, sort = "id",
            direction = Sort.Direction.DESC)Pageable pageable, Model model){

        /*pagingNoticeList noticeList*/
        Page<NoticeDto> pagingNoticeList = noticeService.NoticeList(pageable);

        Long totalCount = pagingNoticeList.getTotalElements();
        int totalPage = pagingNoticeList.getTotalPages();
        int pageSize = pagingNoticeList.getSize();
        int nowPage = pagingNoticeList.getNumber();
        int blockNum = 3;

        int startPage = (int)((Math.floor(nowPage/blockNum)*blockNum) + 1 <= totalPage ?
                (Math.floor(nowPage/blockNum)*blockNum) + 1 : totalPage);
        int endPage = (startPage + blockNum - 1 < totalPage ? startPage + blockNum - 1 : totalPage);

        if(!pagingNoticeList.isEmpty()){
            model.addAttribute("noticeList", pagingNoticeList);
            model.addAttribute("startPage", startPage);
            model.addAttribute("endPage", endPage);
            return "notice/List";
        }
        System.out.println("조회할 공지사항이 없다.");

        return "notice/List";
    }
     /*
    Todo
     1. rladpwls1843@gamil.com
     2. 공지사항 검색 페이지로 이동
     3.
     4.
     */
    @GetMapping("/search")
    public String getNoticeSearch(
            @RequestParam(value = "ID", required = false) String noticeId,
            @RequestParam(value = "search", required = false) String noticeSearch,
            Model model){
        List<NoticeDto> noticeDtoList = noticeService.NoticeListSearch(noticeId,noticeSearch);

        if(!noticeDtoList.isEmpty()){
            model.addAttribute("noticeList", noticeDtoList);
            return "notice/List";
        }
        return "redirect:notice/List";
    }

    @GetMapping("/detail/{id}")
    public String getNoticeDetail(@PathVariable("id") Long id, Model model){
        NoticeDto noticeDto = noticeService.NoticeDetail(id);

        model.addAttribute("noticeDto", noticeDto);
//        공지사항 로그인 하면 보이게 하는지?
//        model.addAttribute("myUserDetails", myUserDetails);
        return "notice/detail";
    }

}