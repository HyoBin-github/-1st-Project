package org.project.dev.notice.dto;

import lombok.*;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InquiryDto {
    private Long inqId; // 공지사항 아이디
    private String ingType;
    private String inqTitle; // 공지사항 글 제목
    private String inqContent; // 공지사항 글 내용
    private String inqWriter; // 공지사항 글 작성자
    private int inqHit;  // 공지사항 글 조회수
    private LocalDateTime createDate; // 공지사항 생성시간
    private LocalDateTime updateDate; // 공지사항 수정시간
}
