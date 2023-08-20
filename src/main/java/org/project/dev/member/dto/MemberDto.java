package org.project.dev.member.dto;

import lombok.*;
import org.project.dev.constrant.Role;
import org.project.dev.member.entity.MemberEntity;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class MemberDto {

    private Long memberId;

    @NotBlank
    @Size(min = 10, max = 255)
    private String memberEmail;

    @NotBlank
    @Size(min = 8, max = 255)
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&^./,+=-])[A-Za-z\\d@$!%*#?&^./,+=-]{8,}$",
            message = "최소 하나의 특수문자, 알파벳, 숫자를 포함해야 합니다")
    private String memberPassword;

    @NotBlank
    @Size(min = 2, max = 50)
    @Pattern(regexp = "^[가-힣a-zA-Z]*$", message = "한글과 영문만 입력 가능합니다" )
    private String memberName;

    @NotBlank
    @Size(min = 2, max = 15)
    private String memberNickName;

    @NotBlank
    @Size(min = 10, max = 11)
    @Pattern(regexp = "(01[016789])(\\d{3,4})(\\d{4})", message = "올바른 휴대전화번호를 입력해주세요")
    private String memberPhone;

    @NotBlank
    @Size(min = 8, max = 8)
    private String memberBirth;

    @NotBlank
    @Size(min = 2, max = 255)
    private String memberAddress;

    private Role role;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

    public static MemberDto toMemberDto(MemberEntity memberEntity) {
        MemberDto memberDto=new MemberDto();
            memberDto.setMemberId(memberEntity.getMemberId());
            memberDto.setMemberEmail(memberEntity.getMemberEmail());
            memberDto.setMemberPassword(memberEntity.getMemberPassword());
            memberDto.setMemberName(memberEntity.getMemberName());
            memberDto.setMemberNickName(memberEntity.getMemberNickName());
            memberDto.setMemberPhone(memberEntity.getMemberPhone());
            memberDto.setMemberBirth(memberEntity.getMemberBirth());
            memberDto.setMemberAddress(memberEntity.getMemberAddress());
            memberDto.setRole(memberEntity.getRole());
            memberDto.setCreateTime(memberEntity.getCreateTime());
            memberDto.setUpdateTime(memberEntity.getUpdateTime());
            return memberDto;
    }



}
