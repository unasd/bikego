package kr.co.bikego.controller.admin;

import kr.co.bikego.dto.*;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.service.FaqService;
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
@RequestMapping("/admin/faq")
public class AdmnFaqController {
    private FaqService faqService;
    private AttachService attachService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqFaq");
        searchDto.setYnDel(""); // 삭제여부 관계없이 전부조회
        HashMap result = faqService.getList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("entityPage"));
        model.addAttribute("resultList", result.get("dtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("entityPage")));
        model.addAttribute("searchDto", searchDto);

        return "faq/list";
    }

    @GetMapping("/write.do")
    public String write() {
        return "faq/write";
    }

    @PostMapping("/write.do")
    public String write(FaqDto faqDto, String[] image, String[] imageName, String[] imageSize,
                        @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        faqDto.setWriterFaq(accountDto.getNameAccount());
        faqDto.setRegdtFaq(LocalDateTime.now());
        faqService.save(faqDto, image, imageName, imageSize);

        return "redirect:/admin/faq/list.do";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, FaqDto faqDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {
        List<AttachDto> attachDtoList = null;

        FaqDto resultDto = faqService.getDetail(faqDto.getSeqFaq());

        attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());
        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "faq/detail";
    }

    @GetMapping("/update.do")
    public String update(Model model, FaqDto faqDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {

        FaqDto resultDto = faqService.getDetail(faqDto.getSeqFaq());
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "faq/update";
    }

    @PostMapping("/update.do")
    public String update(Model model, FaqDto faqDto, SearchDto searchDto , String[] image, String[] imageName, String[] imageSize,
                         HttpServletResponse response, HttpServletRequest request , RedirectAttributes redirectAttr,
                         @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        redirectAttr.addAttribute("seqFaq", faqDto.getSeqFaq());
        faqDto.setModdtFaq(LocalDateTime.now());
        faqDto.setModifierFaq(accountDto.getNameAccount());
        this.faqService.updateNotice(faqDto, image, imageName, imageSize);
        return "redirect:/admin/faq/update.do";
    }

    @DeleteMapping("/delete.do")
    public String delete(FaqDto faqDto, HttpServletResponse response) throws Exception {
        faqService.updateYnDel(faqDto.getSeqFaq());
        return "redirect:/admin/faq/list.do";
    }
}
