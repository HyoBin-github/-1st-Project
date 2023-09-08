package org.project.dev.member.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.config.member.MyUserDetails;
import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
public class MemberController {

    private final MemberService memberService;

    // Create
    @GetMapping("/join")
    public String getJoin(MemberDto memberDto){
        return "member/join";
    }

    @PostMapping("/join")
    public String postJoin(@Valid MemberDto memberDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "member/join";
        }
        // 비밀번호 일치 확인
        if (!memberDto.getMemberPassword().equals(memberDto.getConfirmPassword())) {
            bindingResult.rejectValue("confirmPassword", "error.confirmPassword", "비밀번호가 일치하지않습니다");
            return "member/join";
        }

        memberService.insertMember(memberDto);
        return "login";
    }

    //  Login
    @GetMapping("/login")
    public String getLogin(){
        return "member/login";
    }

    // Read - 회원 목록 조회
    @GetMapping("/memberList")
    public String getMemberList(@AuthenticationPrincipal MyUserDetails myUserDetails, Model model){
        List<MemberDto> memberDtoList=memberService.listMember();

        model.addAttribute("memberDtoList", memberDtoList);
        model.addAttribute("myUserDetails", myUserDetails);
        return "member/memberList";
    }

    // Detail - 회원 상세 보기
    @GetMapping("/detail/{memberId}")
    public String getDetail(@PathVariable("memberId") Long memberId, Model model,
                         @AuthenticationPrincipal MyUserDetails myUserDetails){

        MemberDto member=memberService.detailMember(memberId);

        model.addAttribute("member", member);
        model.addAttribute("myUserDetails", myUserDetails);

        return "member/detail";
    }

    // Update - 회원 수정 화면
    @GetMapping("/update/{memberId}")
    public String getUpdate(@PathVariable("memberId") Long memberId, MemberDto memberDto, Model model){

        memberDto=memberService.updateViewMember(memberId);
        model.addAttribute("memberDto", memberDto);

        return "member/update";
    }

    // Update - 실제 실행
    @PostMapping("/update")
    public String postUpdate(@Valid MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            System.out.println("유효성 검증 관련 오류 발생");
            return "redirect:/";
        }

        int rs = memberService.updateMember(memberDto);

        if (rs == 1) {
            System.out.println("회원정보 수정 성공");
            return "redirect:/member/detail/" + memberDto.getMemberId(); // 수정된 정보를 보여주는 상세 페이지로 이동
        } else {
            System.out.println("회원정보 수정 실패");
            return "redirect:/";
        }
    }

    // Delete - 회원 정보 삭제
    @GetMapping("/delete/{memberId}")
    public String getDelete(@PathVariable("memberId") Long memberId){

        int rs=memberService.deleteMember(memberId);

        if (rs==1) {
            System.out.println("회원정보 삭제 성공");

            // 회원정보 삭제 후 로그아웃 처리
            Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
            if (authentication!=null) {
                SecurityContextHolder.clearContext();
            }
            return "redirect:/";

        }else{
            System.out.println("회원정보 삭제 실패");
            return "redirect:/";
        }
    }

    // 비밀번호 변경
    @GetMapping("/changePassword/{memberId}")
    public String getChangePasswordPage(@PathVariable("memberId") Long memberId, Model model) {

        model.addAttribute("memberId", memberId);
        return "member/changePassword"; // changePassword.html 페이지로 이동
    }

    @PostMapping("/changePassword")
    public String postChangePassword(@RequestParam("memberId") Long memberId,
                                     @RequestParam("currentPassword") String currentPassword,
                                     @RequestParam("newPassword") String newPassword,
                                     @RequestParam("confirmNewPassword") String confirmNewPassword,
                                     MemberDto memberDto, Model model) {

        // 새로운 비밀번호 확인과 일치하는지 검사
        if (!newPassword.equals(confirmNewPassword)) {
            // 일치하지 않을 때 처리 (예: 오류 메시지 표시)
            return "redirect:/member/changePassword"; // 비밀번호 변경 페이지로 다시 이동
        }

        // 비밀번호 변경 메서드 호출
        boolean success = memberService.changePassword(memberId, currentPassword, newPassword, memberDto);

        if (success) {
            // 비밀번호 변경 성공 시 처리 (예: 메시지 표시)
            System.out.println("비밀번호 변경 성공");
            return "redirect:/member/detail/" + memberDto.getMemberId(); // 수정된 정보를 보여주는 상세 페이지로 이동
        } else {
            // 비밀번호 변경 실패 시 처리 (예: 오류 메시지 표시)
            System.out.println("비밀번호 변경 실패");
            return "redirect:/member/changePassword"; // 비밀번호 변경 페이지로 다시 이동
        }
    }

    // 입력한 현재비밀번호와 DB에 있는 현재비밀번호 일치하는지
    @PostMapping("/checkCurrentPassword")
    @ResponseBody
    public Map<String, Boolean> checkCurrentPassword(@RequestParam("currentPassword") String currentPassword,
                                                     @RequestParam("memberId") Long memberId) {

        boolean valid = memberService.checkCurrentPassword(memberId, currentPassword);

        Map<String, Boolean> response = new HashMap<>();
        response.put("valid", valid);
        return response;
    }

    // 정보 수정 전 비밀번호 확인 - 입력 화면
    @GetMapping("/confirmPassword/{memberId}")
    public String getConfirmPasswordView(@PathVariable("memberId") Long memberId, Model model){

        model.addAttribute("memberId", memberId);
        return "member/confirmPassword";
    }

    @PostMapping("/confirmPassword")
    public String postConfirmPassword(@RequestParam("currentPassword") String currentPassword,
                                      @RequestParam("memberId") Long memberId,
                                      MemberDto memberDto){

        boolean valid=memberService.checkCurrentPassword(memberId, currentPassword);

        if (valid) {
            return "redirect:/member/changePassword/" + memberDto.getMemberId(); // 비밀번호 수정 페이지로 이동
        } else {
            return "redirect:/member/confirmPassword/" + memberDto.getMemberId(); // 다시 비밀번호 확인 페이지로 이동
        }
    }

    // 회원(소셜로그인 사용자 포함) 탈퇴 전 이메일 인증 확인 - 입력 화면
    @GetMapping("/confirmEmail/{memberId}")
    public String getConfirmEmailView(@PathVariable("memberId") Long memberId,
                                      @AuthenticationPrincipal MyUserDetails myUserDetails,
                                      Model model){

        MemberDto member=memberService.detailMember(memberId);

        model.addAttribute("member", member);
        model.addAttribute("myUserDetails", myUserDetails);

        return "member/confirmEmail";
    }




}
