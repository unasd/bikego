package kr.co.bikego.controller;

import kr.co.bikego.domain.code.AsStat;
import kr.co.bikego.dto.AccountDto;
import kr.co.bikego.dto.AsDto;
import kr.co.bikego.dto.AttachDto;
import kr.co.bikego.dto.SearchDto;
import kr.co.bikego.service.AsService;
import kr.co.bikego.service.AttachService;
import kr.co.bikego.util.AES256Util;
import kr.co.bikego.util.PageRequest;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;

@Controller
@AllArgsConstructor
@RequestMapping("/as")
public class AsController {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    AES256Util aes;
    private AsService asService;
    private AttachService attachService;

    /**
     * AS리스트 조회
     * @param model
     * @param pageable
     * @param searchDto
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @GetMapping("/list.do")
    public String list(Model model, final PageRequest pageable, SearchDto searchDto) throws Exception {

        pageable.setSortProp("seqAs"); // 페이징 필수셋팅 값, 정렬기준
//        pageable.setListSize(20);
//        pageable.setPageSize(5);
//        pageable.setPageSize(1);
//        pageable.setDirection(Sort.Direction.DESC);
        HashMap result = asService.getAsList(pageable.of(), searchDto);

        pageable.pagination((Page) result.get("asEntityPage"));
        model.addAttribute("asList", result.get("asDtoList"));
        model.addAttribute("pagingResult", pageable.pagination((Page) result.get("asEntityPage")));
        model.addAttribute("searchDto", searchDto);
        return "as/list";
    }

    /**
     * AS작성페이지 이동
     * @return
     */
    @GetMapping("/write.do")
    public String write() {
        return "as/write";
    }

    /**
     * AS작성
     * @param asDto
     * @param image
     * @param imageName
     * @param imageSize
     * @return
     * @throws GeneralSecurityException
     * @throws UnsupportedEncodingException
     */
    @PostMapping("/write.do")
    public String write(AsDto asDto, String[] image, String[] imageName, String[] imageSize) throws Exception {
        asDto.setStatAs(AsStat.A);
        asDto.setYnDel("N");
        asDto.setRegdtAs(LocalDateTime.now());
        asDto.setPasswordAs(passwordEncoder.encode(asDto.getPasswordAs()));
        asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
        asService.saveAs(asDto, image, imageName, imageSize);
        return "redirect:/as/list.do";
    }

    /**
     * 비밀번호 체크
     * @param model
     * @param asDto
     * @return
     */
    @PostMapping("/passwordChk.do")
    @ResponseBody
    public Object passwordChk(Model model, @RequestBody AsDto asDto) throws Exception {
        return asService.passwordChk(asDto);
    }

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
            , HttpServletResponse response, HttpServletRequest request, @AuthenticationPrincipal AccountDto accountDto) throws Exception {

        List<AttachDto> attachDtoList = null;

        AsDto resultDto = asService.getAsDetail(asDto.getSeqAs());
        resultDto.setPasswordAs(request.getParameter("passwordAs"));

        // 20221010 업체조회 가능하도록 수정
        if(accountDto == null) {
            // accountDto 가 없으면 패스워드 체크
            if(!asService.passwordChk(asDto)) {
                resultDto = null;
            }
        } else {
            // accountDto 가 있으면 권한체크
            if(!accountDto.getRoleAccount().equals("VIEWER")) {
                resultDto = null;
            }
        }

        if(resultDto != null) {
            attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("attachDtoList", attachDtoList);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "as/detail";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
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
        if(asService.passwordChk(asDto)) {
            AsDto resultDto = asService.getAsDetail(asDto.getSeqAs());
            List<AttachDto> attachDtoList = attachService.getAttachInfoList(resultDto.getIdAttach());

            resultDto.setPasswordAs(asDto.getPasswordAs());
            model.addAttribute("resultDto", resultDto);
            model.addAttribute("attachDtoList", attachDtoList);
            model.addAttribute("searchDto", searchDto);
            model.addAttribute("pagingResult", pageable);
            return "as/update";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
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
    public String update(Model model, AsDto asDto, SearchDto searchDto , String[] image, String[] imageName, String[] imageSize
            , HttpServletResponse response, HttpServletRequest request , RedirectAttributes redirectAttr) throws Exception {
        if (asService.passwordChk(asDto)) {
            redirectAttr.addAttribute("seqAs", asDto.getSeqAs());
            redirectAttr.addAttribute("passwordAs", asDto.getPasswordAs());
            asDto.setModdtAs(LocalDateTime.now());
            asDto.setPasswordAs(passwordEncoder.encode(asDto.getPasswordAs()));
            asDto.setNoTelAs(aes.encrypt(asDto.getNoTelAs()));
            asService.updateAs(asDto, image, imageName, imageSize);
            return "redirect:/as/detail.do";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
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
        if (asService.passwordChk(asDto)) {
            // 삭제여부 업데이트
            attachService.updateDelYn(asDto);
            resultMap.put("result", "success");
        } else {
            resultMap.put("result", "passwordFail");
        }

        return resultMap;
    }

    /**
     * AS삭제
     * @param asDto
     * @param response
     * @return
     * @throws Exception
     */
    @DeleteMapping("/delete.do")
    public String delete(AsDto asDto, HttpServletResponse response) throws Exception {
        if (asService.passwordChk(asDto)) {
            asService.updateYnDel(asDto.getSeqAs());
            return "redirect:/as/list.do";
        } else {
            response.setContentType("text/html; charset=UTF-8");
            PrintWriter out = response.getWriter();
            out.println("<script>alert('잘못된 접근입니다.'); history.back();</script>");
            out.flush();
            return null;
        }
    }
}
