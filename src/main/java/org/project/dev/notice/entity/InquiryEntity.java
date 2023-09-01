package org.project.dev.notice.entity;

import lombok.*;
import org.project.dev.notice.dto.InquiryDto;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "inquiry")
public class InquiryEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "inq_id")
    private Long inqId; // 공지사항 ID
    // inqID
    @Column(name = "inq_type")
    private String inqType;

    @Column(name = "inq_title", nullable = false)
    private String inquiryTitle; // 문의사항 글 제목

    @Column(name = "inqContent", nullable = false)
    private String inquiryContent; // 문의사항 글 내용

    @Column(name = "inqWriter", nullable = false)
    private String inqWriter; // 문의사항 글 작성자

    @Column(name = "inqHit", nullable = false)
    private int inqHit; // 문의사항 글 조회수

    public static InquiryEntity toInquiryEntityInsert(InquiryDto inquiryDto) {
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInqType(inquiryDto.getInqType());
        inquiryEntity.setInquiryTitle(inquiryDto.getInquiryTitle());
        inquiryEntity.setInquiryContent(inquiryDto.getInquiryContent());
        inquiryEntity.setInqWriter(inquiryDto.getInqWriter());
        inquiryEntity.setInqHit(0);
        return inquiryEntity;
    }

    public static InquiryEntity toInquiryEntityUpdate(InquiryDto inquiryDto) {
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInquiryTitle(inquiryDto.getInquiryTitle());
        inquiryEntity.setInquiryContent(inquiryDto.getInquiryContent());
        inquiryEntity.setInqWriter(inquiryDto.getInqWriter());
        inquiryEntity.setInqHit(inquiryDto.getInqHit());
        return inquiryEntity;
    }
}
