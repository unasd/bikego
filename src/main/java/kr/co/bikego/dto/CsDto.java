package kr.co.bikego.dto;

import kr.co.bikego.domain.code.CsCategory;
import kr.co.bikego.domain.entity.CsEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

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
    private String ynReply;
    private CsCategory categoryCs;

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
                .ynReply(ynReply)
                .categoryCs(categoryCs)
                .build();

        return csEntity;
    }

    @Builder
    public CsDto(Long seqCs, String titleCs, String contentsCs, String replyCs, String noCsTel, String emailCs,
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
