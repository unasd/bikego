package kr.co.bikego.controller;

import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AsService;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/as")
public class AsController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private AsService asService;
    private AttachService attachService;

    /**
     * AS리스트 조회
     * @param model
     * @param pageable
     * @param searchDto
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqAs"); // 페이징 필수셋팅 값, 정렬기준
//        pageable.setListSize(20);
//        pageable.setPageSize(5);
//        pageable.setPageSize(1);
//        pageable.setDirection(Sort.Direction.DESC);
        HashMap result = asService.getAsList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("asEntityPage"));
        model.addAttribute("asList", result.get("asDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("asEntityPage")));
        model.addAttribute("searchDto", searchDto);
        return "as/list";
    }

    /**
     * AS작성페이지 이동
     * @return
     */
    @GetMapping("/write.do")
    public String write() {
        return "as/write";
    }

    /**
     * AS작성
     * @param asDto
     * @param image
     * @param imageName
     * @param imageSize
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/write.do")
    public String write(AsDto asDto, String[] image, String[] imageName, String[] imageSize) throws Exception {
        asDto.setRegdtAs(LocalDateTime.now());
        asDto.setPasswordAs(passwordEncoder.encode(asDto.getPasswordAs()));
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        asService.saveAs(asDto, image, imageName, imageSize);
        return "redirect:/as/list.do";
    }

    /**
     * 비밀번호 체크
     * @param model
     * @param asDto
     * @return
     */
    @PostMapping("/passwordChk.do")
    @ResponseBody
    public Object passwordChk(Model model, @RequestBody AsDto asDto) throws Exception {
        return asService.passwordChk(asDto);
    }

    /**
     * AS 상세보기
     * @param model
     * @param asDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/detail.do")
    public String detail(Model model, AsDto asDto, SearchDto searchDto, final PageRequest pageable, HttpServletResponse response) throws Exception {
        if(asService.passwordChk(asDto)) {
            AsDto resultDto = asService.getAsDetail(asDto.getSeqAs());
            List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

            model.addAttribute("resultDto", resultDto);
            model.addAttribute("attachDtoList", attachDtoList);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "as/detail";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @GetMapping("/update.do")
    public String update(Model model, AsDto asDto, SearchDto searchDto, final PageRequest pageable, HttpServletResponse response) throws Exception {
        if(asService.passwordChk(asDto)) {
            AsDto resultDto = asService.getAsDetail(asDto.getSeqAs());
            List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

            resultDto.setPasswordAs(asDto.getPasswordAs());
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("attachDtoList", attachDtoList);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "as/update";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }

    @PostMapping("/update.do")
    public String update(AsDto asDto, String[] image, String[] imageName, String[] imageSize) throws Exception {
        asDto.setRegdtAs(LocalDateTime.now());
        asDto.setPasswordAs(passwordEncoder.encode(asDto.getPasswordAs()));
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        asService.saveAs(asDto, image, imageName, imageSize);

        return "redirect:/as/detail.do";
    }
}
