package org.project.dev.member.entity;

import lombok.*;
import org.project.dev.cartNew.entity.CartEntity;
import org.project.dev.constrant.Role;
import org.project.dev.member.dto.SemiMemberDto;
import org.project.dev.utils.BaseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "semiMember_project")
public class SemiMemberEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "semiMember_id")
    private Long semiMemberId;

    @Column(name = "semiMember_email", unique = true, nullable = false)
    private String semiMemberEmail;

    @Column(name = "semiMember_phone", unique = true, nullable = false)
    private String semiMemberPhone;

    @Enumerated(EnumType.STRING)
    private Role role;

    @OneToOne(mappedBy = "semiMember", cascade = CascadeType.REMOVE)
    private CartEntity cart;

    public static SemiMemberEntity toSemiMemberEntityInsert(SemiMemberDto semimemberDto, PasswordEncoder passwordEncoder) {

        SemiMemberEntity semiMemberEntity=new SemiMemberEntity();

        semiMemberEntity.setSemiMemberEmail(semimemberDto.getSemiMemberEmail());
        semiMemberEntity.setSemiMemberPhone(passwordEncoder.encode(semimemberDto.getSemiMemberPhone()));
        semiMemberEntity.setRole(Role.SEMIMEMBER);

        return semiMemberEntity;
    }


}
