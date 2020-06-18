package kr.co.bikego.controller;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

import java.util.List;



@Controller
@AllArgsConstructor
public class MainController {

    @RequestMapping(value="/" ) 
    public String index() {
        return "index.html";
    }





  /*  @GetMapping("/")
    public String list(Model model){
        //List<CrudDto> crudList = crudService.getCrudList();

        //model.addAttribute("crudList", crudList);
        return "main/list";
    }*/


}


