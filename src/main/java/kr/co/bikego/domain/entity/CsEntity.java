package kr.co.bikego.domain.entity;

import kr.co.bikego.domain.code.CsCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "cs_board")
public class CsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cs_seq")
    private Long seqCs;

    @Column(length = 100, nullable = false, name = "cs_title")
    private String titleCs;

    @Column(columnDefinition = "TEXT", nullable = false, name = "cs_contents")
    private String contentsCs;

    @Column(columnDefinition = "TEXT", nullable = true, name = "cs_reply")
    private String replyCs;

    @Column(length = 20, nullable = false, name = "cs_tel_no")
    private String noCsTel;

    @Column(length = 100, nullable = false, name = "cs_email")
    private String emailCs;

    @Column(length = 100, nullable = false, name = "cs_password")
    private String passwordCs;

    @Column(length = 20, nullable = false, name = "cs_writer", updatable = false)
    private String writerCs;

    @Column(nullable = true, name = "cs_regdt", updatable = false)
    private LocalDateTime regdtCs;

    @Column(length = 20, nullable = true, name = "cs_modifier")
    private String modifierCs;

    @Column(nullable = true, name = "cs_moddt")
    private LocalDateTime moddtCs;

    @Column(length = 1, nullable = false, name = "del_yn")
    private String ynDel;

    @Column(length = 1, nullable = false, name = "reply_yn")
    private String ynReply;

    @Column(length = 1, nullable = false, name = "cs_category")
    @Enumerated(EnumType.STRING)
    private CsCategory categoryCs;

    @Builder
    public CsEntity(Long seqCs, String titleCs, String contentsCs, String replyCs, String noCsTel, String emailCs,
                    String passwordCs, String writerCs, LocalDateTime regdtCs, String modifierCs, LocalDateTime moddtCs,
                    String ynDel, String ynReply, CsCategory categoryCs) {
        this.seqCs = seqCs;
        this.titleCs = titleCs;
        this.contentsCs = contentsCs;
        this.replyCs = replyCs;
        this.noCsTel = noCsTel;
        this.emailCs = emailCs;
        this.passwordCs = passwordCs;
        this.writerCs = writerCs;
        this.regdtCs = regdtCs;
        this.modifierCs = modifierCs;
        this.moddtCs = moddtCs;
        this.ynDel = ynDel;
        this.ynReply = ynReply;
        this.categoryCs = categoryCs;
    }
}
