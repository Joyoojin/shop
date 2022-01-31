package com.shop.dto;

import com.shop.entity.Notice;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

/**
 * (+) 게시판
 */
@Getter
@Setter
public class NoticeFormDto {

    private static ModelMapper modelMapper = new ModelMapper();

    private Long noticeID;          //게시글 id

    @NotBlank(message = "제목은 필수 입력 값입니다.")
    private String noticeTitle;     //제목

    private String noticeContent;   //내용

    private LocalDateTime regTime;  //등록날짜

    private int noticeHit;          //조회수

    public static NoticeFormDto of(Notice notice) {
        return modelMapper.map(notice, NoticeFormDto.class);
    }

    public Notice createNotice() {
        return modelMapper.map(this, Notice.class);
    }


}