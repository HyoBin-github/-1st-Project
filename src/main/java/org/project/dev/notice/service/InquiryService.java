package org.project.dev.notice.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.member.entity.MemberEntity;
import org.project.dev.member.entity.SemiMemberEntity;
import org.project.dev.notice.dto.InquiryDto;
import org.project.dev.notice.dto.NoticeDto;
import org.project.dev.notice.entity.InquiryEntity;
import org.project.dev.notice.entity.NoticeEntity;
import org.project.dev.notice.repository.InquiryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
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
//    @Transactional
//    public int InquiryInsert(InquiryDto inquiryDto) throws IOException {
//        // file 을 위한 throws
//        InquiryEntity inquiryEntity = InquiryEntity.toInquiryEntityInsert(inquiryDto);
//
//        Long inquiryId = inquiryRepository.save(inquiryEntity).getInqId();
//
//        Optional<InquiryEntity> optionalInquiryEntity
//                = Optional.ofNullable(inquiryRepository.findById(inquiryId).orElseThrow(() ->{
//            return new IllegalArgumentException("문의사항을 찾을 수 없습니다.");
//        }));
//        if(!optionalInquiryEntity.isPresent()){
//            return 0;
//        }
//        return 1;
//    }

    // 송원철 / write 시 memberId 저장
    @Transactional
    public int InquiryInsert(InquiryDto inquiryDto, MemberEntity memberEntity, SemiMemberEntity semiMemberEntity) throws IOException {
        // file 을 위한 throws
        InquiryEntity inquiryEntity = InquiryEntity.toInquiryEntityInsert(inquiryDto);

        inquiryEntity.setMember(memberEntity); // 현재 로그인한 사용자의 MemberEntity 설정

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

    /*
 Todo
  1. rladpwls1843@gamil.com
  2. 문의사항 목록 서비스
  3.
  4.
  */
    public Page<InquiryDto> inquiryList(Pageable pageable, String inquirySelect, String inquirySearch) {

       Page<InquiryEntity> inquiryEntities = null; // 기본 null값으로 설정

        if(inquirySelect.equals("title")){
            inquiryEntities = inquiryRepository.findByInquiryTitleContaining(pageable,inquirySearch);
        }else if(inquirySelect.equals("content")){
            inquiryEntities = inquiryRepository.findByInquiryContentContaining(pageable,inquirySearch);
//        }else if(inquirySelect.equals("writer")){
//            inquiryEntities = inquiryRepository.findByInquiryWriterContaining(pageable,inquirySearch); // 송원철, 주석처리함
        }else{
            inquiryEntities = inquiryRepository.findAll(pageable);
        }


        inquiryEntities.getNumber();
        inquiryEntities.getTotalElements();
        inquiryEntities.getTotalPages();
        inquiryEntities.getSize();

        Page<InquiryDto> inquiryDtoPage = inquiryEntities.map(InquiryDto::toinquiryDto);


        return inquiryDtoPage;
    }

    @Transactional
    public InquiryDto InquiryDetail(Long id) {

        InquiryHit(id);

        InquiryEntity inquiryEntity = inquiryRepository.findById(id).orElseThrow(IllformedLocaleException::new);

        return InquiryDto.builder()
                .inqId(inquiryEntity.getInqId())
                .inquiryTitle(inquiryEntity.getInquiryTitle())
                .inquiryContent(inquiryEntity.getInquiryContent())
                .inqType(inquiryEntity.getInqType())
//                .inquiryWriter(inquiryEntity.getInquiryWriter())
                .CreateTime(inquiryEntity.getCreateTime())
                .UpdateTime(inquiryEntity.getUpdateTime())
                .inqHit(inquiryEntity.getInqHit())
                .memberEmail(inquiryEntity.getMember().getMemberEmail()) // 송원철 / memberEmail 추가
                .build();

    }

    /*
       Todo
        1. rladpwls1843@gamil.com
        2. 상세페이지 선택할 떄마다 조회수 올라감
        3.
        4.
        */
    @Transactional
    private void InquiryHit(Long id) {
        inquiryRepository.InquiryHit(id);
    }

    /*
    TODO
    문의사항 수정하기 서비스
    */
    public InquiryDto InquiryUpdate(Long id) {
        Optional<InquiryEntity> optionalInquiryEntity
                = Optional.ofNullable(inquiryRepository.findById(id).orElseThrow(() ->{
            return new IllegalArgumentException("수정할 문의사항이 없습니다.");
        }));
        if(optionalInquiryEntity.isPresent()){
            InquiryDto inquiryDto = InquiryDto.toinquiryDto(optionalInquiryEntity.get());
            return inquiryDto;
        }
        return null;
    }
    @Transactional
    public InquiryDto inquiryUpdateOk(InquiryDto inquiryDto, Long id) {

        InquiryEntity inquiryEntity = inquiryRepository.findById(id).orElseThrow(()->{
            throw new IllegalArgumentException("수정할 공지사항이 존재하지 않습니다.");
        });

        Long inquiryId = inquiryRepository.save(InquiryEntity.toInquiryEntityUpdate(inquiryDto)).getInqId(); // 수정을 위한 jparepository

        InquiryEntity inquiryEntity1 = inquiryRepository.findById(inquiryId).orElseThrow(()->{
            throw new IllegalArgumentException("수정할 공지사항이 존재하지 않습니다.");
        });
        return InquiryDto.toinquiryDto(inquiryEntity1);
    }

    /*
               Todo
                1. rladpwls1843@gamil.com
                2. 문의사항 삭제 서비스
                3.
                4.
                */
    public int InquiryDelete(Long id) {
        Optional<InquiryEntity> optionalInquiryEntity
                = Optional.ofNullable(inquiryRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("삭제할 문의사항이 없습니다.");
        }));
        inquiryRepository.delete(optionalInquiryEntity.get());

        Optional<InquiryEntity> optionalInquiryEntity1 = inquiryRepository.findById(id);
        if(!optionalInquiryEntity.isPresent()){
            return 1;
        }
        return 0;
    }



}
