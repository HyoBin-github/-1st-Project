package org.project.dev.member.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.config.MyUserDetails;
import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.service.MemberService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;
import java.util.List;


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

        memberService.insertMember(memberDto);
        return "member/login";
    }

    // Login
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

    // Update - 수정 화면
    @GetMapping("/update/{memberId}")
    public String getUpdate(@PathVariable("memberId") Long memberId, Model model){

        MemberDto member=memberService.updateViewMember(memberId);
        model.addAttribute("member", member);

        return "member/update";
    }

    // Update - 실제 실행
    @PostMapping("/update")
    public String postUpdate(@Valid MemberDto memberDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
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

    // Delete
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


}
