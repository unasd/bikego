package kr.co.bikego.controller;

import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.test.service.CrudService;


import kr.co.bikego.util.PageRequest;
import kr.co.bikego.service.PopupService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.dto.PopupinfoDto;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/popup")
public class PopupController {
    private PopupService popupService;
    private AttachService attachService;


    @GetMapping("/list")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws GeneralSecurityException, UnsupportedEncodingException {

        pageable.setSortProp("popupSeq"); // 페이징 필수셋팅 값, 정렬기준
//        pageable.setListSize(20);
//        pageable.setPageSize(5);
//        pageable.setPageSize(1);
//        pageable.setDirection(Sort.Direction.DESC);
        HashMap result = popupService.getPopupList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("popupinfoEntityPage"));
        model.addAttribute("popupList", result.get("popupinfoDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("popupinfoEntityPage")));
        model.addAttribute("searchDto", searchDto);
        return "popup/list";
    }

    @GetMapping("/post")
    public String write() {
        return "popup/write";
    }

    @PostMapping("/post")
    public String write(PopupinfoDto popupinfoDto, String[] image, String[] imageName, String[] imageSize) {
        popupinfoDto.setPopupRegdt(LocalDateTime.now());
        popupService.savePost(popupinfoDto, image, imageName, imageSize);
        return "redirect:/popup/list";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        PopupinfoDto popupinfoDto  = popupService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(popupinfoDto.getAttachId());

        model.addAttribute("popupinfoDto", popupinfoDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "popup/detail";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        PopupinfoDto popupinfoDto = popupService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(popupinfoDto.getAttachId());
        model.addAttribute("popupinfoDto", popupinfoDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "popup/update";
    }

    @PutMapping("/post/edit/{no}")
    public String update(PopupinfoDto popupinfoDto , String[] image, String[] imageName, String[] imageSize) {
        //popupService.savePost(popupinfoDto, null, null, null);
        popupService.savePost(popupinfoDto, image, imageName, imageSize);

        return "redirect:/popup/list";
    }

    @DeleteMapping("/post/delete/{no}")
    public String delete(@PathVariable("no") Long no) {
        popupService.deletePost(no);

        return "redirect:/popup/list";
    }
}
