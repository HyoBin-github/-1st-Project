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
    private String ingType;
    @Column(name = "inq_title", nullable = false)
    private String inqTitle; // 공지사항 글 제목
    @Column(name = "inqContent", nullable = false)
    private String inqContent; // 공지사항 글 내용
    @Column(name = "inqWriter", nullable = false)
    private String inqWriter; // 공지사항 글 작성자
    @Column(name = "inqHit", nullable = false)
    private int inqHit; // 공지사항 글 조회수

    public static InquiryEntity toInquiryEntityInsert(InquiryDto inquiryDto) {
        InquiryEntity inquiryEntity = new InquiryEntity();
        inquiryEntity.setInqTitle(inquiryDto.getInqTitle());
        inquiryEntity.setInqContent(inquiryDto.getInqContent());
        inquiryEntity.setInqWriter(inquiryDto.getInqWriter());
        inquiryEntity.setInqHit(0);
        return inquiryEntity;
    }
}
