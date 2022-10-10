package kr.co.bikego.controller.admin;

import kr.co.bikego.dto.*;
import kr.co.bikego.service.CsService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/cs")
public class AdmnCsController {
    @Autowired
    AES256Util aes;
    private CsService csService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqCs");
        searchDto.setYnDel(""); // 삭제여부 관계없이 전부조회
        HashMap result = csService.getList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("entityPage"));
        model.addAttribute("resultList", result.get("dtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("entityPage")));
        model.addAttribute("searchDto", searchDto);

        return "cs/list";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, CsDto csDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {
        CsDto resultDto = csService.getDetail(csDto.getSeqCs());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "cs/detail";
    }

    @GetMapping("/update.do")
    public String update(Model model, CsDto csDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {

        CsDto resultDto = csService.getDetail(csDto.getSeqCs());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "cs/update";
    }

    @PostMapping("/update.do")
    public String update(Model model, CsDto csDto, SearchDto searchDto , HttpServletResponse response, HttpServletRequest request,
                         RedirectAttributes redirectAttr, @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        redirectAttr.addAttribute("seqCs", csDto.getSeqCs());
        csDto.setNoCsTel(aes.encrypt(csDto.getNoCsTel()));
        csDto.setModdtCs(LocalDateTime.now());
        csDto.setModifierCs(accountDto.getNameAccount());
        this.csService.updateCsAdmin(csDto);
        return "redirect:/admin/cs/detail.do";
    }

    @DeleteMapping("/delete.do")
    public String delete(CsDto csDto, HttpServletResponse response) throws Exception {
        csService.delete(csDto.getSeqCs());
        return "redirect:/admin/cs/list.do";
    }
}
