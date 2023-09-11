package org.project.dev.notice.repository;


import org.project.dev.notice.entity.InquiryEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InquiryRepository extends JpaRepository<InquiryEntity, Long> {
    Page<InquiryEntity> findByInquiryTitleContaining(Pageable pageable, String title); // 제목
    Page<InquiryEntity> findByInquiryContentContaining(Pageable pageable, String content); // 내용
    Page<InquiryEntity> findByInquiryWriterContaining(Pageable pageable, String inquirySearch); // 작성자

    @Modifying(clearAutomatically = true)
    @Query(value = "UPDATE inquiry i set i.inq_hit = i.inq_hit+1 where i.inq_id = :id", nativeQuery = true)
    void InquiryHit(@Param("id") Long id);


}

