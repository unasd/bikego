package kr.co.bikego.controller;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.spec.AsSpecs;
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
import org.springframework.web.bind.annotation.RequestParam;

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

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, @RequestParam(required = false) Map<String, Object> searchRequest) throws GeneralSecurityException, UnsupportedEncodingException {
        Map<AsSpecs.SearchKey, Object> searchKeys = new HashMap<>();
        for(AsSpecs.SearchKey enumKey : AsSpecs.SearchKey.values()) {
            if(searchRequest.get(enumKey.toString().toLowerCase()) != null) {
                searchKeys.put(enumKey, searchRequest.get(enumKey.toString().toLowerCase()));
            }
        }

        System.out.println("searchKeys :: " + searchKeys);

        pageable.setSortProp("seqAs"); // 페이징 필수셋팅 값, 정렬기준
        pageable.setListSize(10);
//        pageable.setPageSize(5);
//        pageable.setPageSize(1);
//        pageable.setDirection(Sort.Direction.DESC);

        HashMap result = asService.getAsList(searchKeys, pageable.of());

        pageable.pagination((Page) result.get("asEntityPage"));
        model.addAttribute("asList", result.get("asDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("asEntityPage")));
        model.addAttribute("searchKeys", searchKeys);
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
