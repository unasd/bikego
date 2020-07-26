package kr.co.bikego.controller;

import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.ShopInfoService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/shopInfo")
public class ShopInfoController {
    @Autowired
    AES256Util aes;
    private ShopInfoService shopInfoService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setListSize(4);
        pageable.setSortProp("seqShop");
        HashMap result = shopInfoService.getList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("entityPage"));
        model.addAttribute("resultList", result.get("dtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("entityPage")));
        model.addAttribute("searchDto", searchDto);

        return "shopInfo/list";
    }
}
