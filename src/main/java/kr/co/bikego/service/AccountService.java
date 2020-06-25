package kr.co.bikego.service;

import kr.co.bikego.domain.entity.AccountEntity;
import kr.co.bikego.domain.repository.AccountRepository;
import kr.co.bikego.dto.AccountDto;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@AllArgsConstructor
@Service
public class AccountService implements UserDetailsService {
    private AccountRepository accountRepository;

    public Long saveAccount(AccountDto accountDto) {
        return accountRepository.save(accountDto.toEntity()).getSeqAccount();
    }

    @Override
    public UserDetails loadUserByUsername(String idAccount) throws UsernameNotFoundException {
        AccountEntity accountEntity = accountRepository.findByIdAccount(idAccount).orElseThrow(() -> new IllegalArgumentException("존재하지 않는 사용자입니다."));

        AccountDto accountDto = AccountDto.builder()
                .idAccount(accountEntity.getIdAccount())
                .passwordAccount(accountEntity.getPasswordAccount())
                .nameAccount(accountEntity.getNameAccount())
                .emailAccount(accountEntity.getEmailAccount())
                .roleAccount(accountEntity.getRoleAccount())
                .build();

        return accountDto;
    }
}
