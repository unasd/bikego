package kr.co.bikego.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/popup")
public class PopupController {

    @GetMapping("/list")
    public String list(Model model){
        //List<CrudDto> crudList = crudService.getCrudList();

        //model.addAttribute("crudList", crudList);
        return "popup/list";
    }
}
