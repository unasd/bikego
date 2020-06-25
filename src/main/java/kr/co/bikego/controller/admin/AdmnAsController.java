package kr.co.bikego.controller.admin;

import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AsService;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@AllArgsConstructor
@RequestMapping("/admin/as")
public class AdmnAsController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private AsService asService;
    private AttachService attachService;

    /**
     * AS 상세보기
     * @param model
     * @param asDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/detail.do")
    public String detail(Model model, AsDto asDto, SearchDto searchDto, final PageRequest pageable
            , HttpServletResponse response, HttpServletRequest request) throws Exception {

        AsDto resultDto = null;
        List<AttachDto> attachDtoList = null;
        Map<String, ?> flashMap = RequestContextUtils.getInputFlashMap(request);

        // 일반적인 상세조회
        if(flashMap == null) {
            resultDto = asService.getAsDetail(asDto.getSeqAs());
        }
        System.out.println("resultDto1 ;; " + resultDto);

        // update.do 처리후 redirect 될 때
        if(resultDto == null && flashMap != null) {
            System.out.println("resultDto2 ;; " + resultDto);
            asDto.setSeqAs((Long) flashMap.get("seqAs"));
            resultDto = asService.getAsDetail(asDto.getSeqAs());
        }

        System.out.println("resultDto3 ;; " + resultDto);

        attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());
        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "as/detail";
    }

    /**
     * AS 수정화면 이동
     * @param model
     * @param asDto
     * @param searchDto
     * @param pageable
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/update.do")
    public String update(Model model, AsDto asDto, SearchDto searchDto
            , final PageRequest pageable, HttpServletResponse response) throws Exception {
        AsDto resultDto = asService.getAsDetail(asDto.getSeqAs());
        List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

        model.addAttribute("resultDto", resultDto);
        model.addAttribute("attachDtoList", attachDtoList);
        model.addAttribute("searchDto", searchDto);
        model.addAttribute("pagingResult", pageable);
        return "as/update";
    }

    /**
     * AS 수정처리
     * @param asDto
     * @param image
     * @param imageName
     * @param imageSize
     * @return
     * @throws Exception
     */
    @PostMapping("/update.do")
    public String update(Model model, AsDto asDto, SearchDto searchDto
            , String[] image, String[] imageName, String[] imageSize
            , HttpServletResponse response, HttpServletRequest request) throws Exception {
        FlashMap fm = RequestContextUtils.getOutputFlashMap(request);
        fm.put("seqAs", asDto.getSeqAs());

        asDto.setModdtAs(LocalDateTime.now());
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        asService.updateAs(asDto, image, imageName, imageSize);

        return "redirect:/as/detail.do";
    }

    /**
     * AS이미지 삭제
     * @param asDto
     * @return
     * @throws Exception
     */
    @PostMapping(value = "/asImgDelete.do")
    @ResponseBody
    public Object asImgDelete(@RequestBody AsDto asDto) throws Exception {
        HashMap<String,Object> resultMap = new HashMap<>();
        attachService.updateDelYn(asDto);
        resultMap.put("result", "success");

        return resultMap;
    }
}
