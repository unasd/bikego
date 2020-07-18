package kr.co.bikego.controller;

import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.PartinfoDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.service.PartService;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/part")
public class PartController {
    private PartService partService;
    private AttachService attachService;


    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws GeneralSecurityException, UnsupportedEncodingException {

        pageable.setSortProp("partSeq"); // 페이징 필수셋팅 값, 정렬기준
        HashMap result = partService.getPartList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("partEntityPage"));
        model.addAttribute("partList", result.get("partinfoDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("partEntityPage")));
        model.addAttribute("searchDto", searchDto);
        return "part/list";
    }

    @GetMapping("/post.do")
    public String write() {
        return "part/write";
    }

    @PostMapping("/save.do")
    public String write(PartinfoDto partinfoDto, String[] image, String[] imageName, String[] imageSize) {
        partinfoDto.setPartRegdt(LocalDateTime.now());
        partService.savePost(partinfoDto, image, imageName, imageSize);
        return "redirect:/part/list.do";
    }

    @GetMapping("/detail.do")
    public String detail(@RequestParam("partSeq") long no, Model model) {
        PartinfoDto partinfoDto  = partService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(partinfoDto.getAttachId());

        model.addAttribute("partinfoDto", partinfoDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "part/detail";
    }

    @GetMapping("/edit.do")
    public String edit(@RequestParam("partSeq") long no, Model model) {
        PartinfoDto partinfoDto = partService.getPost(no);
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(partinfoDto.getAttachId());
        model.addAttribute("partinfoDto", partinfoDto);
        model.addAttribute("attachDtoList", attachDtoList);
        return "part/update";
    }

    @PutMapping("/update.do")
    public String update(PartinfoDto partinfoDto , String[] image, String[] imageName, String[] imageSize) {
        //partService.savePost(partinfoDto, null, null, null);
        partService.savePost(partinfoDto, image, imageName, imageSize);

        return "redirect:/part/list.do";
    }

    @DeleteMapping("/delete.do")
    public String delete(@RequestParam("partSeq") long no) {
        partService.deletePost(no);

        return "redirect:/part/list.do";
    }
}
