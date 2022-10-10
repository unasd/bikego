package kr.co.bikego.controller.admin;

import kr.co.bikego.dto.AccountDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.dto.ShopInfoDto;
import kr.co.bikego.service.ShopInfoService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.HashMap;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/shopInfo")
public class AdmnShopInfoController {
    @Autowired
    AES256Util aes;
    private ShopInfoService shopInfoService;

    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setListSize(4);
        pageable.setSortProp("seqShop");
        searchDto.setYnDel(""); // 삭제여부 관계없이 전부조회
        HashMap result = shopInfoService.getList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("entityPage"));
        model.addAttribute("resultList", result.get("dtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("entityPage")));
        model.addAttribute("searchDto", searchDto);

        return "shopInfo/list";
    }

    @GetMapping("/write.do")
    public String write() {
        return "shopInfo/write";
    }

    @PostMapping("/write.do")
    public String write(ShopInfoDto shopInfoDto, @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        shopInfoDto.setWriterShop(accountDto.getNameAccount());
        shopInfoDto.setYnDel("N");
        shopInfoDto.setRegdtShop(LocalDateTime.now());
        shopInfoDto.setNoTelShop(aes.encrypt(shopInfoDto.getNoTelShop()));
        shopInfoService.save(shopInfoDto);
        return "redirect:/admin/shopInfo/list.do";
    }

    @GetMapping("/detail.do")
    public String detail(Model model, ShopInfoDto shopInfoDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {
        ShopInfoDto resultDto = shopInfoService.getDetail(shopInfoDto.getSeqShop());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "shopInfo/detail";
    }

    @GetMapping("/update.do")
    public String update(Model model, ShopInfoDto shopInfoDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {

        ShopInfoDto resultDto = shopInfoService.getDetail(shopInfoDto.getSeqShop());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "shopInfo/update";
    }

    @PostMapping("/update.do")
    public String update(Model model, ShopInfoDto shopInfoDto, SearchDto searchDto , HttpServletResponse response, HttpServletRequest request,
                         RedirectAttributes redirectAttr, @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        redirectAttr.addAttribute("seqShop", shopInfoDto.getSeqShop());
        shopInfoDto.setModdtShop(LocalDateTime.now());
        shopInfoDto.setModifierShop(accountDto.getNameAccount());
        shopInfoDto.setNoTelShop(aes.encrypt(shopInfoDto.getNoTelShop()));
        shopInfoService.updateShopInfo(shopInfoDto);

        return "redirect:/admin/shopInfo/update.do";
    }

    @DeleteMapping("/delete.do")
    public String delete(ShopInfoDto shopInfoDto, HttpServletResponse response) throws Exception {
        this.shopInfoService.delete(shopInfoDto.getSeqShop());
        return "redirect:/admin/shopInfo/list.do";
    }
}
