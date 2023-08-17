package org.project.dev.notice.dto;

import lombok.*;
import org.project.dev.notice.entity.NoticeEntity;

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
    private LocalDateTime createDate; // 공지사항 생성시간
    private LocalDateTime updateDate; // 공지사항 수정시간

    public static NoticeDto tonoticeDto(NoticeEntity noticeEntity) {
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.setNotId(noticeEntity.getNotId());
        noticeDto.setNotTitle(noticeEntity.getNotTitle());
        noticeDto.setNotContent(noticeEntity.getNotContent());
        noticeDto.setNotWriter(noticeEntity.getNotWriter());
        noticeDto.setNotHit(noticeEntity.getNotHit());
        noticeDto.setCreateDate(noticeEntity.getCreateDate());
        noticeDto.setUpdateDate(noticeEntity.getUpdateDate());
        return noticeDto;
    }
}
