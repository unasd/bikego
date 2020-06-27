package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.AsEntity;
import kr.co.bikego.domain.entity.AsStat;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AsDto {
    private Long seqAs;
    private String titleAs;
    private String contentsAs;
    private String noTelAs;
    private BigDecimal latitudeAs;
    private BigDecimal longitudeAs;
    private String locationAs;
    private String dtlLocationAs;
    private String passwordAs;
    private AsStat statAs;
    private String commentAs;
    private String idAttach;
    private int snFileAttach;
    private String writerAs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regdtAs;
    private String modifierAs;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime moddtAs;
    private String ynDel;

    public AsEntity toEntity() {
        AsEntity asEntity = AsEntity.builder()
                .seqAs(seqAs)
                .titleAs(titleAs)
                .contentsAs(contentsAs)
                .noTelAs(noTelAs)
                .latitudeAs(latitudeAs)
                .longitudeAs(longitudeAs)
                .locationAs(locationAs)
                .dtlLocationAs(dtlLocationAs)
                .passwordAs(passwordAs)
                .statAs(statAs)
                .commentAs(commentAs)
                .idAttach(idAttach)
                .writerAs(writerAs)
                .regdtAs(regdtAs)
                .modifierAs(modifierAs)
                .moddtAs(moddtAs)
                .ynDel(ynDel)
                .build();
        return asEntity;
    }

    @Builder
    public AsDto(Long seqAs, String titleAs, String contentsAs, String noTelAs, BigDecimal latitudeAs, BigDecimal longitudeAs,
                 String locationAs, String dtlLocationAs, String passwordAs, AsStat statAs, String commentAs,
                 String idAttach, int snFileAttah, String writerAs, LocalDateTime regdtAs, String modifierAs, LocalDateTime moddtAs,
                 String ynDel) {
        this.seqAs = seqAs;
        this.titleAs = titleAs;
        this.contentsAs = contentsAs;
        this.noTelAs = noTelAs;
        this.latitudeAs = latitudeAs;
        this.longitudeAs = longitudeAs;
        this.locationAs = locationAs;
        this.dtlLocationAs = dtlLocationAs;
        this.passwordAs = passwordAs;
        this.statAs = statAs;
        this.commentAs = commentAs;
        this.idAttach = idAttach;
        this.snFileAttach = snFileAttah;
        this.writerAs = writerAs;
        this.regdtAs = regdtAs;
        this.modifierAs = modifierAs;
        this.moddtAs = moddtAs;
        this.ynDel = ynDel;
    }
}
