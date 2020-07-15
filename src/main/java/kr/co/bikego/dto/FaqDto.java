package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.FaqEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class FaqDto {
    private Long seqFaq;
    private String titleFaq;
    private String contentsFaq;
    private String idAttach;
    private String writerFaq;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regdtFaq;
    private String modifierFaq;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime moddtFaq;
    private String ynDel;

    public FaqEntity toEntity() {
        FaqEntity faqEntity = FaqEntity.builder()
                .seqFaq(seqFaq)
                .titleFaq(titleFaq)
                .contentsFaq(contentsFaq)
                .idAttach(idAttach)
                .writerFaq(writerFaq)
                .regdtFaq(regdtFaq)
                .modifierFaq(modifierFaq)
                .moddtFaq(moddtFaq)
                .ynDel(ynDel)
                .build();

        return faqEntity;
    }

    @Builder
    public FaqDto(Long seqFaq, String titleFaq, String contentsFaq, String idAttach, String writerFaq,
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
