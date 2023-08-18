package org.project.dev.notice.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.InquiryDto;
import org.project.dev.notice.entity.InquiryEntity;
import org.project.dev.notice.repository.InquiryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InquiryService {

    private final InquiryRepository inquiryRepository;
    /*
   Todo
    1. rladpwls1843@gamil.com
    2. 문의사항 쓰기 서비스
    3.
    4.
    */
    @Transactional
    public int InquiryInsert(InquiryDto inquiryDto) {
        InquiryEntity inquiryEntity = InquiryEntity.toInquiryEntityInsert(inquiryDto);
        Long inquiryId = inquiryRepository.save(inquiryEntity).getInqId();

        Optional<InquiryEntity> optionalInquiryEntity
                = Optional.ofNullable(inquiryRepository.findById(inquiryId).orElseThrow(() ->{
            return new IllegalArgumentException("문의사항을 찾을 수 없습니다.");
        }));
        if(!optionalInquiryEntity.isPresent()){
            return 0;
        }
        return 1;
    }

//    public Page<InquiryDto> InquiryList(Pageable pageable) {}

     /*
  Todo
   1. rladpwls1843@gamil.com
   2. 문의사항 목록 서비스
   3.
   4.
   */
}
