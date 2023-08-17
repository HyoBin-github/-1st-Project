package org.project.dev.dto;

import lombok.*;

import javax.persistence.Column;
import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class NoticeDto {
    private Long notId; // 공지사항 아이디
    private String notTitle; // 공지사항 글 제목
    private String notContent; // 공지사항 글 내용
    private String notWriter; // 공지사항 글 작성자
    private int notHit;  // 공지사항 글 조회수
    private LocalDateTime createDate;

    private LocalDateTime updateDate;

}
