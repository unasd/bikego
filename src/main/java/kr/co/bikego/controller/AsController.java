package kr.co.bikego.controller;

import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AsService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/as")
public class AsController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private AsService asService;

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
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws GeneralSecurityException, UnsupportedEncodingException {

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
    @GetMapping("/post.do")
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
    @PostMapping("/post.do")
    public String write(AsDto asDto, String[] image, String[] imageName, String[] imageSize) throws GeneralSecurityException, UnsupportedEncodingException {
        asDto.setRegdtAs(LocalDateTime.now());
        asDto.setPasswordAs(passwordEncoder.encode(asDto.getPasswordAs()));
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        asService.saveAs(asDto, image, imageName, imageSize);
        return "redirect:/as/list.do";
    }

    /**
     * 비밀번호 입력페이지 이동
     * @param model
     * @param seqAs
     * @return
     */
    @GetMapping("/passwordChk.do")
    public String passwordView(Model model, @RequestParam(value = "seqAs") String seqAs) {
        return "as/passwordChk";
    }

    /**
     * 비밀번호 체크, 상세페이지 이동
     * @param model
     * @param seqAs
     * @return
     */
    @PostMapping("/passwordChk.do")
    public String passwordChk(Model model, @RequestParam(value = "seqAs") String seqAs) {
        return "as/detail";
    }
}
