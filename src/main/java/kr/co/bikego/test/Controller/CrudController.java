package kr.co.bikego.test.Controller;

import kr.co.bikego.test.dto.CrudDto;
import kr.co.bikego.test.service.CrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class CrudController {
    private CrudService crudService;

    @GetMapping("/")
    public String list(Model model){
        List<CrudDto> crudList = crudService.getCrudList();

        model.addAttribute("crudList", crudList);
        return "crud/list.html";
    }

    @GetMapping("/post")
    public String write() {
        return "crud/write.html";
    }

    @PostMapping("/post")
    public String write(CrudDto crudDto) {
        crudService.savePost(crudDto);

        return "redirect:/";
    }

    @GetMapping("/post/{no}")
    public String detail(@PathVariable("no") Long no, Model model) {
        CrudDto crudDto = crudService.getPost(no);

        model.addAttribute("crudDto", crudDto);
        return "crud/detail.html";
    }

    @GetMapping("/post/edit/{no}")
    public String edit(@PathVariable("no") Long no, Model model) {
        CrudDto crudDto = crudService.getPost(no);

        model.addAttribute("crudDto", crudDto);
        return "crud/update.html";
    }

    @PutMapping("/post/edit/{no}")
    public String update(CrudDto crudDto) {
        crudService.savePost(crudDto);

        return "redirect:/";
    }

    @DeleteMapping("/post/{no}")
    public String delete(@PathVariable("no") Long no) {
        crudService.deletePost(no);

        return "redirect:/";
    }
}
