package kr.co.bikego.controller.admin;

import kr.co.bikego.dto.AccountDto;
import kr.co.bikego.service.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@AllArgsConstructor
@RequestMapping("/admin")
public class LoginController {

    @Autowired
    private PasswordEncoder passwordEncoder;
    private AccountService accountService;

    @GetMapping("/login.do")
    public String loginPage(Model model){
        return "/admin/login";
    }

    /**
     * 계정생성 java에서 값을 설정한다
     * @return
     */
    @GetMapping("/saveAccount.do")
    public String saveAccount(){
        AccountDto accountDto = new AccountDto();

        accountDto.setIdAccount("tweeksAdmin");
//        accountDto.setPasswordAccount(passwordEncoder.encode("null")); //
        accountDto.setNameAccount("관리자");
        accountDto.setEmailAccount("master@bikego.co.kr");
        accountDto.setRoleAccount("ADMIN");

        accountService.saveAccount(accountDto);

        return "/main/main";
    }
}
