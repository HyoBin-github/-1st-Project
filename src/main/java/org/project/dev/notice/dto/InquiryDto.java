package org.project.dev.notice.dto;

import lombok.*;
import org.project.dev.notice.entity.InquiryEntity;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class InquiryDto {
    private Long inqId; // 공지사항 아이디
    private String inqType;
    private String inquiryTitle; // 공지사항 글 제목
    private String inquiryContent; // 공지사항 글 내용
    private String inquiryWriter; // 공지사항 글 작성자
    private int inqHit;  // 공지사항 글 조회수
    private LocalDateTime CreateTime; // 공지사항 생성시간
    private LocalDateTime UpdateTime; // 공지사항 수정시간

    public static InquiryDto toinquiryDto(InquiryEntity inquiryEntity) {
        InquiryDto inquiryDto = new InquiryDto();
        inquiryDto.setInqId(inquiryEntity.getInqId());
        inquiryDto.setInqType(inquiryEntity.getInqType());
        inquiryDto.setInquiryTitle(inquiryEntity.getInquiryTitle());
        inquiryDto.setInquiryContent(inquiryEntity.getInquiryContent());
        inquiryDto.setInquiryWriter(inquiryEntity.getInquiryWriter());
        inquiryDto.setInqHit(inquiryEntity.getInqHit());
        inquiryDto.setCreateTime(inquiryEntity.getCreateTime());
        inquiryDto.setUpdateTime(inquiryEntity.getUpdateTime());
        return inquiryDto;
    }
}

