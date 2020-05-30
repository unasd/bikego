package kr.co.bikego.controller;

import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.test.service.CrudService;
import kr.co.bikego.service.PopupService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.dto.PopupinfoDto;

import java.time.LocalDateTime;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/popup")
public class PopupController {
    private PopupService popupService;
    private AttachService attachService;

    @GetMapping("/list")
    public String list(Model model){
        List<PopupinfoDto> popupList = popupService.getPopupList();

        model.addAttribute("popupList", popupList);
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

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        popupService.deletePost(no);

        return "redirect:/popup/list";
    }
}
