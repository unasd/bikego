package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.AsEntity;
import lombok.*;

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
    private String idAttach;
    private String writerAs;
    private LocalDateTime regdtAs;
    private String modifierAs;
    private LocalDateTime moddtAs;

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
                .idAttach(idAttach)
                .writerAs(writerAs)
                .regdtAs(regdtAs)
                .modifierAs(modifierAs)
                .moddtAs(moddtAs)
                .build();
        return asEntity;
    }

    @Builder
    public AsDto(Long seqAs, String titleAs, String contentsAs, String noTelAs, BigDecimal latitudeAs, BigDecimal longitudeAs,
                 String locationAs, String dtlLocationAs, String passwordAs, String idAttach, String writerAs,
                 LocalDateTime regdtAs, String modifierAs, LocalDateTime moddtAs) {
        this.seqAs = seqAs;
        this.titleAs = titleAs;
        this.contentsAs = contentsAs;
        this.noTelAs = noTelAs;
        this.latitudeAs = latitudeAs;
        this.longitudeAs = longitudeAs;
        this.locationAs = locationAs;
        this.dtlLocationAs = dtlLocationAs;
        this.passwordAs = passwordAs;
        this.idAttach = idAttach;
        this.writerAs = writerAs;
        this.regdtAs = regdtAs;
        this.modifierAs = modifierAs;
        this.moddtAs = moddtAs;
    }
}
