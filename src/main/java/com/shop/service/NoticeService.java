package com.shop.service;

import com.shop.dto.NoticeFormDto;
import com.shop.entity.Notice;
import com.shop.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.List;

//게시판

@Service
@Transactional
@RequiredArgsConstructor
public class NoticeService {

    private final NoticeRepository noticeRepository;

    // 등록
    @Transactional
    public Long saveNotice(NoticeFormDto noticeFormDto) throws Exception {
        Notice notice = noticeFormDto.createNotice();
        noticeRepository.save(notice);
        return notice.getNoticeID();
    }


    //검색
    public List<Notice> searchNotices(String keyword) {
        List<Notice> noticeList = noticeRepository.findByNoticeTitleContaining(keyword);

        return noticeList;
    }

    //상세조회
    @Transactional
    public NoticeFormDto getNoticeDtl(Long noticeID) {

        Notice notice = noticeRepository.findById(noticeID)
                .orElseThrow(EntityNotFoundException::new);
        NoticeFormDto noticeFormDto = NoticeFormDto.of(notice);
        return noticeFormDto;
    }

    /* 조회수 Counting */
    @Transactional
    public int updateNoticeHit(Long id) {
        return noticeRepository.updateNoticeHit(id);
    }

    //전체조회
    @Transactional
    public List<Notice> findNotices() {
        return noticeRepository.findAll();
    }

    //리스트 가져오기. 페이징 포함.
    public Page<Notice> getNoticeList(int page) {
        return noticeRepository.findAll(PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "noticeID")));
    }

    //수정
    public Long updateNotice(NoticeFormDto noticeFormDto) throws Exception {
        Notice notice = noticeRepository.findById(noticeFormDto.getNoticeID())
                .orElseThrow(EntityNotFoundException::new);
        notice.updateNotice(noticeFormDto);

        return notice.getNoticeID();
    }

    //삭제하기
    @Transactional
    public void deleteNotice(Long noticeID) {
        noticeRepository.deleteById(noticeID);
    }


}