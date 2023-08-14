package org.project.dev.entity;

import lombok.*;
import org.project.dev.utils.BaseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Entity
@Table(name = "member_tb")
public class MemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    @Column(name = "member_password", nullable = false)
    private String password;

    @Column(name = "member_email", unique = true)
    private String email; //ID 역활

    @Column(name = "member_username", nullable = false)
    private String userName; //실명

    @Column(name = "member_nickname", nullable = false)
    private String nickName; // 닉네임은 그냥 랜덤 단어 돌려서 생성해주는거 있음 좋을꺼같음
}
