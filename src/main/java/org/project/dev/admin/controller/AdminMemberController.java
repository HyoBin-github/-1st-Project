package org.project.dev.admin.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.admin.dto.AdminMemberDto;
import org.project.dev.admin.service.AdminMemberService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class AdminMemberController {

    private final AdminMemberService adminMemberService;

    @GetMapping("/list")
    public Map<String,Object> getMemberList(){
        Map<String,Object> response = new HashMap<>();
        response.put("memberList",adminMemberService.getMemberList());
        return response;
    }
    @GetMapping("/detail")
    public Map<String,Object> getMemberDetail(@RequestParam("id")Long memberId){
        Map<String,Object> response = new HashMap<>();
        response.put("member",adminMemberService.getMemberDetail(memberId));
        return response;
    }

    @PostMapping("/insert")
    public Map<String,Object> postMemberInsert(@RequestBody AdminMemberDto adminMemberDto){
        Map<String,Object> response = new HashMap<>();
        response.put("responseCode",adminMemberService.adminMemberInsert(adminMemberDto));
        return response;
    }

    @PostMapping("/update")
    public Map<String,Object> postMemberUpdate(@RequestBody AdminMemberDto adminMemberDto){
        Map<String,Object> response = new HashMap<>();
        response.put("responseCode",adminMemberService.adminMemberUpdate(adminMemberDto));
        return response;
    }
}
