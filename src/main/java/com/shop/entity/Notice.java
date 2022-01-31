package com.shop.entity;

import com.shop.dto.NoticeFormDto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

// 게시판

@Entity
@Table(name = "notice")
@Getter
@Setter
@ToString
public class Notice extends BaseEntity {

    @Id
    @Column(name = "noticeID")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeID;       //공지 글 아이디

    @Column(nullable = false, length = 50)
    private String noticeTitle; //제목

    @Column(nullable = false)
    private String noticeContent; //내용

    @Column(nullable = false)
    private int noticeHit; //조회수

    //수정 업데이트
    public void updateNotice(NoticeFormDto noticeFormDto) {
        this.noticeTitle = noticeFormDto.getNoticeTitle();
        this.noticeContent = noticeFormDto.getNoticeContent();
    }
}