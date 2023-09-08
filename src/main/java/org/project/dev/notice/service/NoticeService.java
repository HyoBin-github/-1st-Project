
package org.project.dev.notice.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.NoticeDto;
import org.project.dev.notice.entity.NoticeEntity;
import org.project.dev.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    /*
   Todo
    1. rladpwls1843@gamil.com
    2. 공지사항 쓰기 서비스
    3.
    4.
    */
    @Transactional
    public int NoticeInsert(NoticeDto noticeDto) {
        NoticeEntity noticeEntity = NoticeEntity.toNoticeEntityInsert(noticeDto);

        Long noticeId = noticeRepository.save(noticeEntity).getNotId(); // entity값을 받아서 repository에 저장

        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeRepository.findById(noticeId).orElseThrow(() -> {
            return new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
        }));
        if(!optionalNoticeEntity.isPresent()){
            return 0;
        }
        return 1;
    }

    /*
  Todo
   1. rladpwls1843@gamil.com
   2. 공지사항 목록 서비스
   3.
   4.
   */
//    public List<NoticeDto> findAllNoticeList() {
//        List<NoticeDto> noticeDtos = new ArrayList<>();
//        List<NoticeEntity> noticeEntities = noticeReopsitory.findAll();
//        /**/
//        if (!noticeEntities.isEmpty()){
//            for (NoticeEntity noticeEntity : noticeEntities) {
//                NoticeDto noticeDto = NoticeDto.builder()
//                        .notTitle(noticeEntity.getNotTitle())
//                        .notContent(noticeEntity.getNotContent())
//                        .notWriter(noticeEntity.getNotWriter())
//                        .notHit(noticeEntity.getNotHit())
//                        .build();
//                noticeDtos.add(noticeDto);
//            }
//        }
//        return noticeDtos;
//    }

    /*
       Todo
        1. rladpwls1843@gamil.com
        2. 공지사항 목록로 이동 -> paging
        3.
        4.
        */
//    public Page<NoticeDto> NoticeList(Pageable pageable) {
//        Page<NoticeEntity> noticeEntities = noticeRepository.findAll(pageable);
//
//        noticeEntities.getNumber();
//        noticeEntities.getTotalElements();
//        noticeEntities.getTotalPages();
//        noticeEntities.getSize();
//
//        Page<NoticeDto> noticeDtoPage = noticeEntities.map(NoticeDto::tonoticeDto);
//
//        return noticeDtoPage;
//    }
    public Page<NoticeDto> NoticeList(Pageable pageable, String noticeSelect, String noticeSearch) {

        Page<NoticeEntity> noticeEntities = noticeRepository.findAll(pageable, noticeSelect, noticeSearch);

        noticeEntities.getNumber();
        noticeEntities.getTotalElements();
        noticeEntities.getTotalPages();
        noticeEntities.getSize();

        Page<NoticeDto> noticeDtoPage = noticeEntities.map(NoticeDto::tonoticeDto);

        return noticeDtoPage;
    }
    /*
       Todo
        1. rladpwls1843@gamil.com
        2. 공지사항 목록로 이동
        3.
        4.type에 해당하는 목록으로 이동
        */
    @Transactional
    public Page<NoticeDto> noticeList(String type, Pageable pageable) {

        Page<NoticeEntity> noticeEntities = noticeRepository.findByNotType(type, pageable); // not_type에 해당하는 값만 출력

        noticeEntities.getNumber();
        noticeEntities.getTotalElements();
        noticeEntities.getTotalPages();
        noticeEntities.getSize();

        Page<NoticeDto> noticeDtoPage = noticeEntities.map(NoticeDto::tonoticeDto);

        return noticeDtoPage;
    }

    /*
       Todo
        1. rladpwls1843@gamil.com
        2. 공지사항 검색페이지로 이동
        3.
        4.
        */
    //            Pageable pageable
    public List<NoticeDto> NoticeListSearch(
            @NotNull String noticeSelect, String noticeSearch) {
//
//        Page<NoticeEntity> noticeEntitiespage = noticeRepository.findAll(pageable);
//
//        noticeEntitiespage.getNumber();
//        noticeEntitiespage.getTotalElements();
//        noticeEntitiespage.getTotalPages();
//        noticeEntitiespage.getSize();

//        Page<NoticeDto> noticeDtoPage = noticeEntitiespage.map(NoticeDto::tonoticeDto);
//        return noticeDtoPage;
//
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        List<NoticeEntity> noticeEntities = new ArrayList<>();

        if(noticeSelect.equals("title")){
            noticeEntities = noticeRepository.findByNoticeTitleContaining(noticeSearch);
        }else if(noticeSelect.equals("content")){
            noticeEntities = noticeRepository.findByNoticeContentContaining(noticeSearch);
        }else if(noticeSelect.equals("writer")){
            noticeEntities = noticeRepository.findByNotWriterContaining(noticeSearch);
        }else{
            noticeEntities = noticeRepository.findAll();
        }

        if(!noticeEntities.isEmpty()){
            for(NoticeEntity noticeEntity : noticeEntities) {
                NoticeDto noticeDto = NoticeDto.builder()
                        .notId(noticeEntity.getNotId())
                        .notType(noticeEntity.getNotType())
                        .noticeTitle(noticeEntity.getNoticeTitle())
                        .noticeContent(noticeEntity.getNoticeContent())
                        .notWriter(noticeEntity.getNotWriter())
                        .CreateTime(noticeEntity.getCreateTime())
                        .UpdateTime(noticeEntity.getUpdateTime())
                        .notHit(noticeEntity.getNotHit())
                        .build();
                noticeDtoList.add(noticeDto);
            }
        }
        return noticeDtoList;
    }
    /*
           Todo
            1. rladpwls1843@gamil.com
            2. 공지사항 상세페이지로 이동
            3.
            4.
            */
    @Transactional // error
    public NoticeDto NoticeDetail(Long id) {

        NoticeHit(id);

        NoticeEntity noticeEntity = noticeRepository.findById(id).orElseThrow(IllformedLocaleException::new);

        return NoticeDto.builder()
                .notId(noticeEntity.getNotId())
                .noticeTitle(noticeEntity.getNoticeTitle())
                .noticeContent(noticeEntity.getNoticeContent())
                .CreateTime(noticeEntity.getCreateTime())
                .UpdateTime(noticeEntity.getUpdateTime())
                .notWriter(noticeEntity.getNotWriter())
                .notHit(noticeEntity.getNotHit())
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
    public void NoticeHit(Long id){
        noticeRepository.NoticeHit(id);
    }

    /*
    TODO
    공지사항 수정하기 서비스
    */
    public NoticeDto NoticeUpdate(Long id) {

        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
        }));
        if(optionalNoticeEntity.isPresent()){
            NoticeDto noticeDto = NoticeDto.tonoticeDto(optionalNoticeEntity.get());

            return noticeDto;
        }
        return null;
    }

    public int NoticeUpdateOk(NoticeDto noticeDto, Long id) {

        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeRepository.findById(noticeDto.getNotId()).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 공지사항이 없습니다."); // id 확인해
        }));

        noticeDto.setNotId(id); // dto에 id 들어감

        Long noticeId = noticeRepository.save(NoticeEntity.toNoticeEntityUpdate(noticeDto)).getNotId(); // 수정을 위한 jparepository.save

        NoticeEntity noticeEntity = NoticeEntity.toNoticeEntityUpdate(noticeDto); // dto->entity

        Optional<NoticeEntity> optionalNoticeEntity1
                = Optional.ofNullable(noticeRepository.findById(noticeDto.getNotId()).orElseThrow(() -> {
            return new IllegalArgumentException("수정한 공지사항이 없습니다.");
        })); //
        if(optionalNoticeEntity1.isPresent()){
            return 1;
        }
        return 0;
    }

    @Transactional
    public int NoticeDelete(Long id) {

        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeRepository.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("삭제할 공지사항이 없습니다.");
        }));
        noticeRepository.delete(optionalNoticeEntity.get());

        Optional<NoticeEntity> optionalNoticeEntity1 = noticeRepository.findById(id);
        if (!optionalNoticeEntity.isPresent()){
            return 1;
        }
        return 0;
    }


}
