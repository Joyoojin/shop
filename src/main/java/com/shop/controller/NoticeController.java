package com.shop.controller;

import com.shop.dto.NoticeFormDto;
import com.shop.entity.Notice;
import com.shop.service.NoticeService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * 추가 _게시판 (등록, 수정, 삭제, 조회 )
 */

@RequestMapping("/boardList")
@Controller
@RequiredArgsConstructor
public class NoticeController {
    private final NoticeService noticeService;


    /* 게시판 등록 */
    @GetMapping(value = "/admin/notice/new")
    public String noticeForm(Model model) {
        model.addAttribute("noticeFormDto", new NoticeFormDto());
        return "board/InsertNotice";
    }

    @RequestMapping(value = "/admin/notice/new")
    public String noticeNew(NoticeFormDto noticeFormDto, Model model) {
        try {
            noticeService.saveNotice(noticeFormDto);
            List<Notice> notices = noticeService.findNotices();  //반환된 페이지에도 데이터 출력
            model.addAttribute("notices", notices);
        } catch (Exception e) {
            model.addAttribute("errorMessage", " 등록 중 에러가 발생하였습니다.");
            System.out.println(e.getMessage());
            return "board/InsertNotice";
        }

        return "redirect:/boardList/notices";
    }

    /*전체 조회 페이지 */

    @GetMapping(value = {"/notices"})
    public String noticeManage(Model model, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        try {
            Page<Notice> noticeList = noticeService.getNoticeList(page);
            int totalPage = noticeList.getTotalPages();

            model.addAttribute("totalPage", totalPage);
            model.addAttribute("noticeList", noticeList.getContent());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "board/noticeList";
    }

    /**
     * 검색
     */
    @GetMapping("/notices/noticeSearch")
    public String search(@RequestParam(value = "keyword") String keyword, Model model) {
        List<Notice> noticeList = noticeService.searchNotices(keyword);
        model.addAttribute("noticeList", noticeList);

        return "board/noticeSearchList";
    }

    /* 게시글 상세 페이지*/
    @GetMapping(value = "/notice/{noticeID}")
    public String noticeDtl(Model model, @PathVariable("noticeID") Long noticeID) {
        NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeID);
        noticeService.updateNoticeHit(noticeID);   // 조회수 증가
        model.addAttribute("notice", noticeFormDto);

        return "board/noticeDtl";
    }

    // 글 삭제 버튼
    @RequestMapping(value = "/notice/delete/{noticeID}", method = {RequestMethod.GET, RequestMethod.POST})
    public String delete(@PathVariable("noticeID") Long noticeID, Model model) {
        noticeService.deleteNotice(noticeID);
        List<Notice> notices = noticeService.findNotices();
        model.addAttribute("notices", notices);

        return "redirect:/boardList/notices";
    }

    //수정하기
    @GetMapping(value = "/notice/edit/{noticeID}")
    public String noticeDtl(@PathVariable("noticeID") Long noticeID, Model model) {

        NoticeFormDto noticeFormDto = noticeService.getNoticeDtl(noticeID);
        model.addAttribute("noticeFormDto", noticeFormDto);

        return "board/InsertNotice";
    }

    @PostMapping("/notice/edit/{noticeID}")
    public String noticeUpdate(@Valid NoticeFormDto noticeFormDto, BindingResult bindingResult,
                               Model model) {
        if (bindingResult.hasErrors()) {
            return "board/InsertNotice";
        }
        try {
            noticeService.updateNotice(noticeFormDto);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생하였습니다.");
            return "board/InsertNotice";
        }
        List<Notice> notices = noticeService.findNotices();
        model.addAttribute("notices", notices);
        return "redirect:/boardList/notices";
    }
}


//삭제 예정
/*전체 조회 페이지2  */
/*    @GetMapping(value = {"/notices2"})
    public String noticeManage2(Model model, @RequestParam(required = false, defaultValue = "0", value = "page") int page) {
        try {
            Page<Notice> noticeList = noticeService.getNoticeList(page);
            int totalPage = noticeList.getTotalPages();

            model.addAttribute("totalPage", totalPage);
            model.addAttribute("noticeList", noticeList.getContent());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return "board/noticeList2";
    }
*/