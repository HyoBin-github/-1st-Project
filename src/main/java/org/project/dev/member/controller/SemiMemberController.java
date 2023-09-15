package org.project.dev.member.controller;

import lombok.RequiredArgsConstructor;
import org.project.dev.config.member.MyUserDetails;
import org.project.dev.config.semiMember.SemiMyUserDetails;
import org.project.dev.member.dto.MemberDto;
import org.project.dev.member.dto.SemiMemberDto;
import org.project.dev.member.service.MemberService;
import org.project.dev.member.service.SemiMemberService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    private final MemberService memberService; // 프로필 이미지 가져오기위함

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

    @GetMapping("/login")
    public String getLogin(){
        return "semiMember/login";
    }


    // Read - 간편회원목록 조회
//    @GetMapping("/semiMemberList")
//    public String getSemiMemberList(@AuthenticationPrincipal MyUserDetails myUserDetails, SemiMyUserDetails semiMyUserDetails, Model model){
//        List<SemiMemberDto> semiMemberDtoList=semiMemberService.listSemiMember();
//
//        model.addAttribute("semiMemberDtoList", semiMemberDtoList);
//        model.addAttribute("semiMyUserDetails", semiMyUserDetails);
//        model.addAttribute("myUserDetails", myUserDetails);
//
//        return "semiMember/semiMemberList";
//    }

    // Read_paging - 회원 목록 조회
    @GetMapping("/pagingList") // page=0 -> DB     // 페이지수, 한페이지 보이는View수 , 정렬
    public String getPagingList(@PageableDefault(page = 0, size = 2, sort = "semiMemberId",
                                direction = Sort.Direction.DESC) Pageable pageable, Model model,
                                @AuthenticationPrincipal SemiMyUserDetails semiMyUserDetails,
                                @AuthenticationPrincipal MyUserDetails myUserDetails){

        // *** Page<>  Pageable
        Page<SemiMemberDto> semiMemberList = semiMemberService.SemiMemberPagingList(pageable);

        long totalCount = semiMemberList.getTotalElements();
        int pageSize = semiMemberList.getSize();

        // 총 글수 17
        // 한페이지 당 size 3
        // 총페이수 6
        // blockNum=3
        //1  2  3    -> 3 3 3
        //4  5  6    -> 3 3 2
        // 블록의 첫페지이 지
        // 블록이 3일 경우     123 -> 1, 456  -> 4 , 789 -> 7
        int nowPage = semiMemberList.getNumber(); // 현재 페이지
        int totalPage = semiMemberList.getTotalPages(); // 총 페이지 수
        int blockNum = 3;

        // Math.floor -> 올림
        int startPage =
                (int)((Math.floor(nowPage/blockNum)*blockNum)+1 <= totalPage ? (Math.floor(nowPage/blockNum)*blockNum)+1 : totalPage);
        // 블록의 마지막 페이지
        // 블록이 3일 경우      123 -> 3, 456  -> 5 , 789 -> 9
        // 시작페이지+블록-1> 전체 페이지 -> 마지막페이지숫자(시작페이지+블록-1)
        int endPage = (startPage + blockNum-1 < totalPage ? startPage + blockNum-1 : totalPage);

        for(int i=startPage;i<=endPage;i++){
            System.out.print(i+" , ");
        }

        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        model.addAttribute("semiMemberList", semiMemberList);
        model.addAttribute("semiMyUserDetails", semiMyUserDetails);
        model.addAttribute("myUserDetails", myUserDetails);

        return "semiMember/pagingList";
    }

    // Detail - 회원 상세 보기
    @GetMapping("/detail/{semiMemberId}")
    public String getDetail(@PathVariable("semiMemberId") Long semiMemberId, Model model,
                            @AuthenticationPrincipal SemiMyUserDetails semiMyUserDetails,
                            @AuthenticationPrincipal MyUserDetails myUserDetails){

        SemiMemberDto semiMember=semiMemberService.detailSemiMember(semiMemberId);
        MemberDto member=memberService.detailMember(myUserDetails.getMemberEntity().getMemberId());

        model.addAttribute("semiMember", semiMember);
        model.addAttribute("member", member);
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
