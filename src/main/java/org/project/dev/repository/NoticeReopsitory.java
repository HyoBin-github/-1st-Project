package org.project.dev.repository;

import org.project.dev.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NoticeReopsitory extends JpaRepository<MemberEntity, Long> {

}
