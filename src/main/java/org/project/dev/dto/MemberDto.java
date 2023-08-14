package org.project.dev.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MemberDto {
    private Long id;

    private String password; //비밀번호 -> 비밀번호 암호화\

    private String email; //user -> userName

    private String name;

    private LocalDateTime createDate;

    private LocalDateTime updateDate;
}
