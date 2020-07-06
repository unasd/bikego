package kr.co.bikego.controller.admin;

import kr.co.bikego.dto.*;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.service.NoticeService;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/noti")
public class AdmnNotiController {
    private NoticeService noticeService;
    private AttachService attachService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqNoti");
        searchDto.setYnDel(""); // 삭제여부 관계없이 전부조회
        HashMap result = noticeService.getNoticeList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("noticeEntityPage"));
        model.addAttribute("noticeList", result.get("noticeDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("noticeEntityPage")));
        model.addAttribute("searchDto", searchDto);

        return "noti/list";
    }

    @GetMapping("/write.do")
    public String write() {
        return "noti/write";
    }

    @PostMapping("/write.do")
    public String write(NoticeDto noticeDto, String[] image, String[] imageName, String[] imageSize,
                        @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        noticeDto.setYnDel("N");
        noticeDto.setWriterNoti(accountDto.getNameAccount());
        noticeDto.setRegdtNoti(LocalDateTime.now());
        noticeService.saveNotice(noticeDto, image, imageName, imageSize);

        return "redirect:/admin/noti/list.do";
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

    @GetMapping("/update.do")
    public String update(Model model, NoticeDto noticeDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {

        NoticeDto resultDto = noticeService.getNoticeDetail(noticeDto.getSeqNoti());
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "noti/update";
    }

    @PostMapping("/update.do")
    public String update(Model model, NoticeDto noticeDto, SearchDto searchDto , String[] image, String[] imageName, String[] imageSize,
                         HttpServletResponse response, HttpServletRequest request , RedirectAttributes redirectAttr,
                         @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        redirectAttr.addAttribute("seqNoti", noticeDto.getSeqNoti());
        noticeDto.setModdtNoti(LocalDateTime.now());
        noticeDto.setModifierNoti(accountDto.getNameAccount());
        noticeService.updateNotice(noticeDto, image, imageName, imageSize);

        return "redirect:/admin/noti/detail.do";
    }

    @PostMapping(value = "/imgDelete.do")
    @ResponseBody
    public Object asImgDelete(@RequestParam String idAttach, int snFileAttach, @AuthenticationPrincipal AccountDto accountDto) throws Exception {
        HashMap<String,Object> resultMap = new HashMap<>();
        // 삭제여부 업데이트
        attachService.updateDelYn(idAttach, snFileAttach, accountDto.getNameAccount());
        resultMap.put("result", "success");

        return resultMap;
    }

    @DeleteMapping("/delete.do")
    public String delete(NoticeDto noticeDto, HttpServletResponse response) throws Exception {
        noticeService.updateYnDel(noticeDto.getSeqNoti());
        return "redirect:/admin/noti/list.do";
    }
}
