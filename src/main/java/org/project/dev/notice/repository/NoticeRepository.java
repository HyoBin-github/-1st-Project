package org.project.dev.notice.repository;

import org.project.dev.notice.entity.NoticeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findByNoticeTitleContaining(String title);
    List<NoticeEntity> findByNotContentContaining(String content);

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE NoticeEntity n set n.not_hit = n.not_hit + 1 where n.not_id = :id" , nativeQuery = true)
    void NoticeHit(@Param("id") Long id);
}
