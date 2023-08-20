
package org.project.dev.notice.service;

import lombok.RequiredArgsConstructor;
import org.project.dev.notice.dto.NoticeDto;
import org.project.dev.notice.entity.NoticeEntity;
import org.project.dev.notice.repository.NoticeRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.IllformedLocaleException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeReopsitory;

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
        Long noticeId = noticeReopsitory.save(noticeEntity).getNotId();

        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeReopsitory.findById(noticeId).orElseThrow(() -> {
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
    public Page<NoticeDto> NoticeList(Pageable pageable) {
        Page<NoticeEntity> noticeEntities = noticeReopsitory.findAll(pageable);

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
    public List<NoticeDto> NoticeListSearch(String noticeId, String noticeSearch) {
        List<NoticeDto> noticeDtoList = new ArrayList<>();
        List<NoticeEntity> noticeEntities = new ArrayList<>();

        if(noticeId.equals("title")){
            noticeEntities = noticeReopsitory.findByNoticeTitleContaining(noticeSearch);
        }else if(noticeId.equals("content")){
            noticeEntities = noticeReopsitory.findByNotContentContaining(noticeSearch);
        }else{
            noticeEntities = noticeReopsitory.findAll();
        }

        if(!noticeEntities.isEmpty()){
            for(NoticeEntity noticeEntity : noticeEntities) {
                NoticeDto noticeDto = NoticeDto.builder()
                        .notTitle(noticeEntity.getNoticeTitle())
                        .notContent(noticeEntity.getNotContent())
                        .notWriter(noticeEntity.getNotWriter())
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
    @Transactional
    public NoticeDto NoticeDetail(Long id) {
        NoticeHit(id);

        NoticeEntity noticeEntity = noticeReopsitory.findById(id).orElseThrow(IllformedLocaleException::new);

        return NoticeDto.builder()
                .notId(noticeEntity.getNotId())
                .notTitle(noticeEntity.getNoticeTitle())
                .notContent(noticeEntity.getNotContent())
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
        noticeReopsitory.NoticeHit(id);
    }

    /*
    TODO
    공지사항 수정하기 서비스
    */
    public NoticeDto NoticeUpdate(Long id) {
        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeReopsitory.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("공지사항을 찾을 수 없습니다.");
        }));
        if(optionalNoticeEntity.isPresent()){
            NoticeDto noticeDto = NoticeDto.tonoticeDto(optionalNoticeEntity.get());
            return noticeDto;
        }
        return null;
    }

    public int NoticeUpdateOk(NoticeDto noticeDto) {
        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeReopsitory.findById(noticeDto.getNotId()).orElseThrow(() -> {
            return new IllegalArgumentException("수정할 공지사항이 없습니다.");
        }));

        NoticeEntity noticeEntity = NoticeEntity.toNoticeEntityUpdate(noticeDto);

        Long noticeId = noticeReopsitory.save(noticeEntity).getNotId();

        Optional<NoticeEntity> optionalNoticeEntity1
                = Optional.ofNullable(noticeReopsitory.findById(noticeId).orElseThrow(() -> {
                return new IllegalArgumentException("수정할 공지사항이 없습니다.");
        }));
        if(optionalNoticeEntity.isPresent()){
            return 1;
        }
        return 0;
    }

    public int NoticeDelete(Long id) {
        Optional<NoticeEntity> optionalNoticeEntity
                = Optional.ofNullable(noticeReopsitory.findById(id).orElseThrow(() -> {
            return new IllegalArgumentException("삭제할 공지사항이 없습니다.");
        }));
        noticeReopsitory.delete(optionalNoticeEntity.get());

        Optional<NoticeEntity> optionalNoticeEntity1 = noticeReopsitory.findById(id);
        if (!optionalNoticeEntity.isPresent()){
            return 1;
        }
        return 0;
    }
}
