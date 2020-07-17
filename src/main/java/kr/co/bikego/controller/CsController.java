package kr.co.bikego.controller;

import kr.co.bikego.dto.CsDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.CsService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/cs")
public class CsController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private CsService csService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqCs");
        HashMap result = csService.getList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("entityPage"));
        model.addAttribute("resultList", result.get("dtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("entityPage")));
        model.addAttribute("searchDto", searchDto);

        return "cs/list";
    }

    @GetMapping("/write.do")
    public String write() {
        return "cs/write";
    }

    @PostMapping("/write.do")
    public String write(CsDto csDto) throws Exception {
        csDto.setYnDel("N");
        csDto.setPasswordCs(passwordEncoder.encode(csDto.getPasswordCs()));
        csDto.setNoCsTel(aes.encrypt(csDto.getNoCsTel()));
        csDto.setRegdtCs(LocalDateTime.now());
        csService.save(csDto);

        return "redirect:/cs/list.do";
    }

    @PostMapping("/passwordChk.do")
    @ResponseBody
    public Object passwordChk(Model model, @RequestBody CsDto csDto) throws Exception {
        return csService.passwordChk(csDto);
    }

    @GetMapping("/detail.do")
    public String detail(Model model, CsDto csDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {
        CsDto resultDto = csService.getDetail(csDto.getSeqCs());
        resultDto.setPasswordCs(request.getParameter("passwordCs"));

        if(!csService.passwordChk(csDto)) {
            resultDto = null;
        }

        if(resultDto != null) {
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "cs/detail";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @GetMapping("/update.do")
    public String update(Model model, CsDto csDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {

        if(csService.passwordChk(csDto)) {
            CsDto resultDto = csService.getDetail(csDto.getSeqCs());

            resultDto.setPasswordCs(csDto.getPasswordCs());
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "cs/update";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @PostMapping("/update.do")
    public String update(Model model, CsDto csDto, SearchDto searchDto , HttpServletResponse response, HttpServletRequest request,
                         RedirectAttributes redirectAttr) throws Exception {

        if(csService.passwordChk(csDto)) {
            redirectAttr.addAttribute("seqCs", csDto.getSeqCs());
            redirectAttr.addAttribute("passwordCs", csDto.getPasswordCs());
            csDto.setModdtCs(LocalDateTime.now());
            csDto.setPasswordCs(passwordEncoder.encode(csDto.getPasswordCs()));
            csDto.setNoCsTel(aes.encrypt(csDto.getNoCsTel()));
            csService.updateNotice(csDto);
            return "redirect:/cs/detail.do";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @DeleteMapping("/delete.do")
    public String delete(CsDto csDto, HttpServletResponse response) throws Exception {
        if(csService.passwordChk(csDto)) {
            csService.updateYnDel(csDto.getSeqCs());
            return "redirect:/cs/list.do";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }
}
