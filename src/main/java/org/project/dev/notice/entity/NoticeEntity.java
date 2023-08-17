package org.project.dev.entity;

import lombok.*;
import org.project.dev.utils.BaseEntity;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "notice")
public class NoticeEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "not_id")
    private Long notId; // 공지사항 ID -> 순서 아님
    // html에 변수 사용하지 않고 다른 방법 해보는 건 어떄욥.
    // 삭제 할 떄 순서가 이상해져서
    @Column(name = "not_title", nullable = false)
    private String notTitle; // 공지사항 글 제목
    @Column(name = "notContent", nullable = false)
    private String notContent; // 공지사항 글 내용
    @Column(name = "notWriter", nullable = false)
    private String notWriter; // 공지사항 글 작성자
    @Column(name = "notHit", nullable = false)
    private int notHit; // 공지사항 글 조회수

    // 연관관계
    // 1:N 관리자
}
