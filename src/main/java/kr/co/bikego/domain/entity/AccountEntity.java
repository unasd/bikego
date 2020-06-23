package kr.co.bikego.domain.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "account")
@Builder
@EqualsAndHashCode(of = "idAccount")
public class AccountEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "account_seq")
    private Integer seqAccount;

    @Column(length = 100, nullable = false, name = "account_id")
    private String idAccount;

    @Column(length = 1000, nullable = false, name = "account_password")
    private String passwordAccount;

    @Column(length = 100, name = "account_name")
    private String nameAccount;

    @Column(length = 100, name = "account_email")
    private String emailAccount;

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    private Set<AccountRole> roles;

}
