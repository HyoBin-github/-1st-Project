package org.project.dev.member.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.member.exception.BadRequestException;
import org.project.dev.member.service.MemberService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/member")
@RequiredArgsConstructor
public class CheckDuplicateController {

    private final MemberService memberService;

    @GetMapping("/memberEmail/check")
    public ResponseEntity<?> checkEmailDuplication(@RequestParam(value = "memberEmail") String memberEmail) throws BadRequestException {
        System.out.println(memberEmail);

        if(memberService.existsByMemberEmail(memberEmail) == true){
            throw new BadRequestException("이미 사용중인 이메일입니다");
        }else{
            return ResponseEntity.ok("사용가능한 이메일입니다");
        }
    }

    @GetMapping("/memberNickName/check")
    public ResponseEntity<?> checkNickNameDuplication(@RequestParam(value = "memberNickName") String memberNickName) throws BadRequestException {
        System.out.println(memberNickName);

        if (memberService.existsByMemberNickName(memberNickName) == true) {
            throw new BadRequestException("이미 사용중인 닉네임입니다");
        }else{
            return ResponseEntity.ok("사용가능한 닉네임입니다");
        }


    }
}
