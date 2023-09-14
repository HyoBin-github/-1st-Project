package org.project.dev.member.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.config.member.MyUserDetails;
import org.project.dev.config.semiMember.SemiMyUserDetails;
import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.dto.SemiMemberDto;
import org.project.dev.member.service.SemiMemberService;
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
@RequestMapping("/semiMember")
@RequiredArgsConstructor
public class SemiMemberController {

    private final SemiMemberService semiMemberService;

    @GetMapping("/join")
    public String getJoin(SemiMemberDto semiMemberDto){

        return "semiMember/join";
    }

    @PostMapping("/join")
    public String postJoin(@Valid SemiMemberDto semiMemberDto, BindingResult bindingResult){

        if (bindingResult.hasErrors()) {
            return "semiMember/join";
        }

        semiMemberService.insertSemiMember(semiMemberDto);
        return "login";
    }

    // Read - 간편회원목록 조회
    @GetMapping("/semiMemberList")
    public String getSemiMemberList(@AuthenticationPrincipal MyUserDetails myUserDetails, SemiMyUserDetails semiMyUserDetails, Model model){
        List<SemiMemberDto> semiMemberDtoList=semiMemberService.listSemiMember();


        model.addAttribute("semiMemberDtoList", semiMemberDtoList);
        model.addAttribute("semiMyUserDetails", semiMyUserDetails);
        model.addAttribute("myUserDetails", myUserDetails);

        return "semiMember/semiMemberList";
    }

    @GetMapping("/login")
    public String getLogin(){
        return "semiMember/login";
    }

    // Detail - 회원 상세 보기
    @GetMapping("/detail/{semiMemberId}")
    public String getDetail(@PathVariable("semiMemberId") Long semiMemberId, Model model,
                            @AuthenticationPrincipal SemiMyUserDetails semiMyUserDetails){

        SemiMemberDto semiMember=semiMemberService.detailSemiMember(semiMemberId);

        model.addAttribute("semiMember", semiMember);
        model.addAttribute("semiMyUserDetails", semiMyUserDetails);

        return "semiMember/detail";
    }

    // Delete - 회원 정보 삭제
    @GetMapping("/delete/{semiMemberId}")
    public String getDelete(@PathVariable("semiMemberId") Long semiMemberId){

        int rs=semiMemberService.deleteSemiMember(semiMemberId);

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

    // 회원(소셜로그인 사용자 포함) 탈퇴 전 이메일 인증 확인 - 입력 화면
    @GetMapping("/confirmEmail/{semiMemberId}")
    public String getConfirmEmailView(@PathVariable("semiMemberId") Long semiMemberId,
                                      @AuthenticationPrincipal SemiMyUserDetails semiMyUserDetails,
                                      Model model){

        SemiMemberDto semiMember=semiMemberService.detailSemiMember(semiMemberId);

        model.addAttribute("semiMember", semiMember);
        model.addAttribute("semiMyUserDetails", semiMyUserDetails);

        return "semiMember/confirmEmail";
    }


}
