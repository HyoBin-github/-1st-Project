package org.project.dev.notice.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.InquiryDto;
import org.project.dev.notice.entity.InquiryEntity;
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
    @Transactional
    public int InquiryInsert(InquiryDto inquiryDto) throws IOException {
        // file 을 위한 throws
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
        }else if(inquirySelect.equals("writer")){
            inquiryEntities = inquiryRepository.findByInquiryWriterContaining(pageable,inquirySearch);
        }else{
            inquiryEntities = inquiryRepository.findAll(pageable);
        }


        inquiryEntities.getNumber();
        inquiryEntities.getTotalElements();
        inquiryEntities.getTotalPages();
        inquiryEntities.getSize();

        Page<InquiryDto> inquiryDtoPage = inquiryEntities.map(InquiryDto::toinquiryDto);

//        if(!inquiryEntities.isEmpty()){
//            for(InquiryEntity inquiryEntity : inquiryEntities){
//                InquiryDto inquiryDto = InquiryDto.builder()
//                        .inqId(inquiryEntity.getInqId())
//                        .inqType(inquiryEntity.getInqType())
//                        .inquiryTitle(inquiryEntity.getInquiryTitle())
//                        .inquiryContent(inquiryEntity.getInquiryContent())
//                        .inquiryWriter(inquiryEntity.getInquiryWriter())
//                        .CreateTime(inquiryEntity.getCreateTime())
//                        .UpdateTime(inquiryEntity.getUpdateTime())
//                        .inqHit(inquiryEntity.getInqHit())
//                        .build();
//                inquiryDtoPage.add(inquiryDtoPage);
//            }
//        }

        return inquiryDtoPage;
    }
    /*
 Todo
  1. rladpwls1843@gamil.com
  2. 문의사항 검색 시 목록 서비스
  3.
  4. 검색과정에서 타임리프 페이징이 들어갈 경우 오류가 발생하여 페이징이 없는 경로 추가 설정
  */
//    public List<InquiryDto> InquiryListSearch(@NotNull String inquirySelect, String inquirySearch) {
//        List<InquiryDto> inquiryDtoList = new ArrayList<>();
//        List<InquiryEntity> inquiryEntities = new ArrayList<>();
//
//        if(inquirySelect.equals("title")){
//            inquiryEntities = inquiryRepository.findByInquiryTitleContaining(inquirySearch);
//        }else if(inquirySelect.equals("content")){
//            inquiryEntities = inquiryRepository.findByInquiryContentContaining(inquirySearch);
//        }else if(inquirySelect.equals("writer")){
//            inquiryEntities = inquiryRepository.findByInquiryWriterContaining(inquirySearch);
//        }else{
//            inquiryEntities = inquiryRepository.findAll();
//        }
//
//        if(!inquiryEntities.isEmpty()){
//            for(InquiryEntity inquiryEntity : inquiryEntities){
//                InquiryDto inquiryDto = InquiryDto.builder()
//                        .inqId(inquiryEntity.getInqId())
//                        .inqType(inquiryEntity.getInqType())
//                        .inquiryTitle(inquiryEntity.getInquiryTitle())
//                        .inquiryContent(inquiryEntity.getInquiryContent())
//                        .inquiryWriter(inquiryEntity.getInquiryWriter())
//                        .CreateTime(inquiryEntity.getCreateTime())
//                        .UpdateTime(inquiryEntity.getUpdateTime())
//                        .inqHit(inquiryEntity.getInqHit())
//                        .build();
//                inquiryDtoList.add(inquiryDto);
//            }
//        }
//        return inquiryDtoList;
//    }

    /*
           Todo
            1. rladpwls1843@gamil.com
            2. 문의사 상세페이지로 이동
            3.
            4.
            */
    @Transactional
    public InquiryDto InquiryDetail(Long id) {

        InquiryHit(id);

        InquiryEntity inquiryEntity = inquiryRepository.findById(id).orElseThrow(IllformedLocaleException::new);

        return InquiryDto.builder()
                .inqId(inquiryEntity.getInqId())
                .inquiryTitle(inquiryEntity.getInquiryTitle())
                .inquiryContent(inquiryEntity.getInquiryContent())
                .inquiryWriter(inquiryEntity.getInquiryWriter())
                .CreateTime(inquiryEntity.getCreateTime())
                .UpdateTime(inquiryEntity.getUpdateTime())
                .inqHit(inquiryEntity.getInqHit())
                .build();

    }

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
    public int InquiryUpdateOk(InquiryDto inquiryDto, Long id) {

        Optional<InquiryEntity> optionalInquiryEntity
                = Optional.ofNullable(inquiryRepository.findById(inquiryDto.getInqId()).orElseThrow(() ->{
            return new IllegalArgumentException("수정할 문의사항이 없습니다.");
        }));

        inquiryDto.setInqId(id);

        Long inquiryId = inquiryRepository.save(InquiryEntity.toInquiryEntityUpdate(inquiryDto)).getInqId(); // 수정을 위한 jparepository.save

        InquiryEntity inquiryEntity = InquiryEntity.toInquiryEntityUpdate(inquiryDto); // dto -> entity

        Optional<InquiryEntity> optionalInquiryEntity1
                = Optional.ofNullable(inquiryRepository.findById(inquiryId).orElseThrow(() ->{
            return new IllegalArgumentException("수정할 문의사항이 없습니다.");
        }));
        if(optionalInquiryEntity1.isPresent()){
            return 1;
        }
        return 0;
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
