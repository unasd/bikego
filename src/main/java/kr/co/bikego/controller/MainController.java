package kr.co.bikego.controller;

import kr.co.bikego.dto.PopupinfoDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import kr.co.bikego.service.PopupService;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.List;



@Controller
@AllArgsConstructor
public class MainController {

    private PopupService popupService;

    @RequestMapping(value="/" )
    public String index() {
        return "redirect:/main.do";
    }


    @GetMapping("main.do")
    public String list(Model model) throws GeneralSecurityException, UnsupportedEncodingException {

            List<PopupinfoDto> popupList= popupService.getPopupMainList();

            model.addAttribute("popupList", popupList);

        return "main/main";
    }

    @GetMapping("bikego.do")
    public String Welist(Model model) throws GeneralSecurityException, UnsupportedEncodingException {

        return "main/info";
    }



}


