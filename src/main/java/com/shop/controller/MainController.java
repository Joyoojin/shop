package com.shop.controller;

import com.shop.dto.ItemSearchDto;
import com.shop.dto.MainItemDto;
import com.shop.service.ItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Optional;
//( 회원가입. 이벤트. 지도. 추가 )

@Controller
@RequiredArgsConstructor
public class MainController {

    private final ItemService itemService;

    //첫 화면인 메인페이지, category/는 원하는 항목 검색페이지
    @GetMapping(value = {"/", "/category/items", "/category/items/{page}"})
    public String main(ItemSearchDto itemSearchDto, Optional<Integer> page, Model model) {

        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);
        Page<MainItemDto> items = itemService.getMainItemPage(itemSearchDto, pageable);

        model.addAttribute("items", items);
        model.addAttribute("itemSearchDto", itemSearchDto);
        model.addAttribute("maxPage", 5);

        return "main";                              //첫 화면 메인페이지
    }


    @GetMapping(value = "/event")                   //이벤트페이지
    public String event() {

        return "board/event";
    }

    @GetMapping(value = "/test")                      //테스트페이지
    public String test() {

        return "test/index";
    }

    @GetMapping(value = "/map")                        //지도페이지
    public String test2() {

        return "test/map";
    }


}
