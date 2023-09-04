package org.project.dev;

import org.junit.jupiter.api.Test;
import org.project.dev.notice.dto.NoticeDto;
import org.project.dev.notice.entity.NoticeEntity;
import org.project.dev.notice.repository.NoticeRepository;
import org.project.dev.notice.service.NoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.swing.text.html.Option;
import java.util.Optional;

@SpringBootTest
public class NoticeTest {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NoticeRepository noticeRepository;
    @Test
    void insertTest(){
        NoticeDto noticeDto = new NoticeDto();
//        noticeDto.setNotTitle("제목1");
        noticeDto.setNotType("배송");
//        noticeDto.setNotContent("내용");
        noticeDto.setNotWriter("관리자");

        NoticeEntity noticeEntity = NoticeEntity.toNoticeEntityInsert(noticeDto);
        Long notion_id = noticeRepository.save(noticeEntity).getNotId();
        Optional<NoticeEntity> noticeEntityOptional = noticeRepository.findById(notion_id);

        if(noticeEntityOptional.isPresent()){
            System.out.println("공지사항작성 성공");
        }else{
            System.out.println("공지사항작성 실패");
        }
    }
}

