package kr.co.bikego.controller;

import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.service.FaqService;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/faq")
public class FaqController {
    private FaqService faqService;
    private AttachService attachService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqFaq");
        HashMap result = faqService.getList(searchDto);

        model.addAttribute("resultList", result.get("dtoList"));

        return "faq/userList";
    }
}
