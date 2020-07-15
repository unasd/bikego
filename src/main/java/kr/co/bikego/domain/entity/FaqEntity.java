package kr.co.bikego.domain.entity;

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
@Table(name = "faq_board")
public class FaqEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "faq_seq")
    private Long seqFaq;

    @Column(name = "faq_title", length = 100, nullable = false)
    private String titleFaq;

    @Column(name = "faq_contents")
    private String contentsFaq;

    @Column(name = "attach_id", length = 30, nullable = true)
    private String idAttach;

    @Column(name = "faq_writer", length = 20, nullable = false, updatable = false)
    private String writerFaq;

    @Column(name = "faq_regdt", nullable = false, updatable = false)
    private LocalDateTime regdtFaq;

    @Column(name = "faq_modifier", length = 20, nullable = true)
    private String modifierFaq;

    @Column(name = "faq_moddt", nullable = true)
    private LocalDateTime moddtFaq;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String ynDel;

    @Builder
    public FaqEntity(Long seqFaq, String titleFaq, String contentsFaq, String idAttach, String writerFaq,
                     LocalDateTime regdtFaq, String modifierFaq, LocalDateTime moddtFaq, String ynDel) {
        this.seqFaq = seqFaq;
        this.titleFaq = titleFaq;
        this.contentsFaq = contentsFaq;
        this.idAttach = idAttach;
        this.writerFaq = writerFaq;
        this.regdtFaq = regdtFaq;
        this.modifierFaq = modifierFaq;
        this.moddtFaq = moddtFaq;
        this.ynDel = ynDel;
    }
}
