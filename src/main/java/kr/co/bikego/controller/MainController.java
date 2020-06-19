package kr.co.bikego.controller;

import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;



@Controller
@AllArgsConstructor
public class MainController {

    @RequestMapping(value="/" ) 
    public String index() {
        return "redirect:/main.do";
    }


    @GetMapping("main.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws GeneralSecurityException, UnsupportedEncodingException {

//        pageable.setSortProp("popupSeq"); // 페이징 필수셋팅 값, 정렬기준
//        pageable.setListSize(20);
//        pageable.setPageSize(5);
//        pageable.setPageSize(1);
//        pageable.setDirection(Sort.Direction.DESC);
/*        HashMap result = popupService.getPopupList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("popupinfoEntityPage"));
        model.addAttribute("popupList", result.get("popupinfoDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("popupinfoEntityPage")));
        model.addAttribute("searchDto", searchDto);*/
        return "main/main";
    }




  /*  @GetMapping("/")
    public String list(Model model){
        //List<CrudDto> crudList = crudService.getCrudList();

        //model.addAttribute("crudList", crudList);
        return "main/list";
    }*/


}


