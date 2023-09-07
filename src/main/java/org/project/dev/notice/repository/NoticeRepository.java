package org.project.dev.notice.repository;


import org.project.dev.notice.entity.NoticeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<NoticeEntity, Long> {

    List<NoticeEntity> findByNoticeTitleContaining(String title); // search - title을 포함하고 있는
    List<NoticeEntity> findByNoticeContentContaining(String content); // search - content를 포함하고 있는
    List<NoticeEntity> findByNotWriterContaining(String writer); // search - writer을 포함하고 있는
    Page<NoticeEntity> findByNotType(String type, Pageable pageable); // list - type에 해당하는

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE notice n set n.not_hit = n.not_hit + 1 where n.not_id = :id" , nativeQuery = true)
    void NoticeHit(@Param("id") Long id);

}

