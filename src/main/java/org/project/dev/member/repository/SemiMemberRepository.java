package org.project.dev.member.repository;

import org.project.dev.member.entity.SemiMemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SemiMemberRepository extends JpaRepository<SemiMemberEntity, Long> {

    boolean existsBySemiMemberEmail(String semiMemberEmail);

    Optional<SemiMemberEntity> findBySemiMemberEmail(String semiMemberEmail);
}
