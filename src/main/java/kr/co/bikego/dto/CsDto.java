package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.CsEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CsDto {
    private Long seqCs;
    private String titleCs;
    private String contentsCs;
    private String replyCs;
    private String noCsTel;
    private String emailCs;
    private String passwordCs;
    private String writerCs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regdtCs;
    private String modifierCs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime moddtCs;
    private String ynDel;

    public CsEntity toEntity() {
        CsEntity csEntity = CsEntity.builder()
                .seqCs(seqCs)
                .titleCs(titleCs)
                .contentsCs(contentsCs)
                .replyCs(replyCs)
                .noCsTel(noCsTel)
                .emailCs(emailCs)
                .passwordCs(passwordCs)
                .writerCs(writerCs)
                .regdtCs(regdtCs)
                .modifierCs(modifierCs)
                .moddtCs(moddtCs)
                .ynDel(ynDel)
                .build();

        return csEntity;
    }

    @Builder
    public CsDto(Long seqCs, String titleCs, String contentsCs, String replyCs, String noCsTel, String emailCs,
                    String passwordCs, String writerCs, LocalDateTime regdtCs, String modifierCs, LocalDateTime moddtCs, String ynDel) {
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
    }
}
