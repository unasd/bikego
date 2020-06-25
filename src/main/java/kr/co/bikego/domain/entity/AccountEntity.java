package kr.co.bikego.domain.entity;

import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "account")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_seq")
    private Long seqAccount;

    @Column(length = 100, nullable = false, name = "account_id")
    private String idAccount;

    @Column(length = 1000, nullable = false, name = "account_password")
    private String passwordAccount;

    @Column(length = 100, name = "account_name")
    private String nameAccount;

    @Column(length = 100, name = "account_email")
    private String emailAccount;

    @Column(length = 10, name = "account_role")
    private String roleAccount;

    @Builder
    public AccountEntity(Long seqAccount, String idAccount, String passwordAccount, String nameAccount, String emailAccount, String roleAccount){
        this.seqAccount = seqAccount;
        this.idAccount = idAccount;
        this.passwordAccount = passwordAccount;
        this.nameAccount = nameAccount;
        this.emailAccount = emailAccount;
        this.roleAccount = roleAccount;
    }
}
