package org.project.dev.notice.repository;

import org.project.dev.entity.MemberEntity;
import org.project.dev.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface NoticeReopsitory extends JpaRepository<NoticeEntity, Long> {
    
    List<NoticeEntity> findByTitleContaining(String title);
    List<NoticeEntity> findByContentContaining(String content);

    @Modifying
    @Query(value = "update NoticeEntity n set n.hit = n.hit +1 where not_id=id")
    void NoticeHit(@Param("id") Long id);
}
