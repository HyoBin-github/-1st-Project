package org.project.dev.member.dto;

import lombok.Getter;
import lombok.Setter;
import org.project.dev.constrant.Role;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;

@Getter
@Setter
public class CombinedMemberDto {

    // member
    private Long memberId;
    private String memberEmail;
    private String memberName;
    private String memberNickName;
    private String memberPhone;
    private String memberBirth;
    private String memberStreetAddress;
    private String memberDetailAddress;
    private Role role;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private String imageUrl;

    // semiMember
    private Long semiMemberId;
    private String semiMemberEmail;
    private String semiMemberPhone;

}
