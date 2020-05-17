package kr.co.bikego.controller;

import kr.co.bikego.test.dto.CrudDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/as")
public class AsController {

    @GetMapping("/list.do")
    public String list(Model model){
        //List<CrudDto> crudList = crudService.getCrudList();

        //model.addAttribute("crudList", crudList);
        return "as/list";
    }

    @GetMapping("/post.do")
    public String write() {
        return "as/write";
    }
}
