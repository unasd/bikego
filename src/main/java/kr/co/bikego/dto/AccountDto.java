package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.AccountEntity;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AccountDto implements UserDetails {

    private Long seqAccount;
    private String idAccount;
    private String passwordAccount;
    private String nameAccount;
    private String emailAccount;
    private String roleAccount;

    public AccountEntity toEntity() {
        AccountEntity accountEntity = AccountEntity.builder()
                .seqAccount(seqAccount)
                .idAccount(idAccount)
                .passwordAccount(passwordAccount)
                .nameAccount(nameAccount)
                .emailAccount(emailAccount)
                .roleAccount(roleAccount)
                .build();
        return accountEntity;
    }

    @Builder
    public AccountDto(Long seqAccount, String idAccount, String passwordAccount, String nameAccount, String emailAccount, String roleAccount) {
        this.seqAccount = seqAccount;
        this.idAccount = idAccount;
        this.passwordAccount = passwordAccount;
        this.nameAccount = nameAccount;
        this.emailAccount = emailAccount;
        this.roleAccount = roleAccount;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        ArrayList<GrantedAuthority> auth = new ArrayList<GrantedAuthority>();
        auth.add(new SimpleGrantedAuthority(roleAccount));
        return auth;
    }

    @Override
    public String getPassword() {
        return passwordAccount;
    }

    @Override
    public String getUsername() {
        return nameAccount;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
