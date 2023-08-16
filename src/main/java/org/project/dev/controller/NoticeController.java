package org.project.dev.controller;

import org.project.dev.dto.NoticeDto;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/notice")
public class NoticeController {

    @GetMapping("/write")
    public String write(NoticeDto noticeDto){
        return "notice/write";
    }


}