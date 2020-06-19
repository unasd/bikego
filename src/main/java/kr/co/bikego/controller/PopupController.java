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


    @GetMapping("/list.do")
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

    @GetMapping("/post.do")
    public String write() {
        return "popup/write";
    }

    @PostMapping("/save.do")
    public String write(PopupinfoDto popupinfoDto, String[] image, String[] imageName, String[] imageSize) {
        popupinfoDto.setPopupRegdt(LocalDateTime.now());
        popupService.savePost(popupinfoDto, image, imageName, imageSize);
        return "redirect:/popup/list.do";
    }

    @GetMapping("/detail.do")
    public String detail(@RequestParam("popupSeq") long no, Model model) {
        PopupinfoDto popupinfoDto  = popupService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(popupinfoDto.getAttachId());

        model.addAttribute("popupinfoDto", popupinfoDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "popup/detail";
    }

    @GetMapping("/edit.do")
    public String edit(@RequestParam("popupSeq") long no, Model model) {
        PopupinfoDto popupinfoDto = popupService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(popupinfoDto.getAttachId());
        model.addAttribute("popupinfoDto", popupinfoDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "popup/update";
    }

    @PutMapping("/update.do")
    public String update(PopupinfoDto popupinfoDto , String[] image, String[] imageName, String[] imageSize) {
        //popupService.savePost(popupinfoDto, null, null, null);
        popupService.savePost(popupinfoDto, image, imageName, imageSize);

        return "redirect:/popup/list.do";
    }

    @DeleteMapping("/delete.do")
    public String delete(@RequestParam("popupSeq") long no) {
        popupService.deletePost(no);

        return "redirect:/popup/list.do";
    }
}
