package kr.co.bikego.controller;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.dto.AsDto;
import kr.co.bikego.service.AsService;
import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable) throws GeneralSecurityException, UnsupportedEncodingException {
        System.out.println("pageable :: " + pageable);
        pageable.setSortProp("seqAs"); // 페이징 필수셋팅 값
        pageable.setSize(10);
        HashMap result = asService.getAsList(pageable.of());
        model.addAttribute("asList", result.get("asDtoList"));
        model.addAttribute("pageable", result.get("asEntityPage"));
        return "as/list";
    }

    @GetMapping("/post.do")
    public String write() {
        return "as/write";
    }

    @PostMapping("/post.do")
    public String write(AsDto asDto, String[] image, String[] imageName, String[] imageSize) throws GeneralSecurityException, UnsupportedEncodingException {
        asDto.setRegdtAs(LocalDateTime.now());
        asDto.setPasswordAs(passwordEncoder.encode(asDto.getPasswordAs()));
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        System.out.println("asDto :: " + asDto.toString());
        asService.saveAs(asDto, image, imageName, imageSize);
        return "redirect:/as/list.do";
    }
}
