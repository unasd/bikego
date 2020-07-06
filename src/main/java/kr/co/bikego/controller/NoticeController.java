package kr.co.bikego.controller;

import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.NoticeDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.service.NoticeService;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/noti")
public class NoticeController {
    private NoticeService noticeService;
    private AttachService attachService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqNoti");
        HashMap result = noticeService.getNoticeList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("noticeEntityPage"));
        model.addAttribute("noticeList", result.get("noticeDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("noticeEntityPage")));
        model.addAttribute("searchDto", searchDto);

        return "noti/list";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, NoticeDto noticeDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<AttachDto> attachDtoList = null;

        NoticeDto resultDto = noticeService.getNoticeDetail(noticeDto.getSeqNoti());

        attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());
        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "noti/detail";
    }
}
