package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.ShopInfoEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class ShopInfoDto {
    private Long seqShop;
    private String nmShop;
    private String addr1Shop;
    private String addr2Shop;
    private String noTelShop;
    private BigDecimal latitudeShop;
    private BigDecimal longitudeShop;
    private String commentShop;
    private String writerShop;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regdtShop;
    private String modifierShop;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime moddtShop;
    private String ynDel;

    public ShopInfoEntity toEntity() {
        ShopInfoEntity shopInfoEntity = ShopInfoEntity.builder()
                .seqShop(seqShop)
                .nmShop(nmShop)
                .addr1Shop(addr1Shop)
                .addr2Shop(addr2Shop)
                .noTelShop(noTelShop)
                .latitudeShop(latitudeShop)
                .longitudeShop(longitudeShop)
                .commentShop(commentShop)
                .writerShop(writerShop)
                .regdtShop(regdtShop)
                .modifierShop(modifierShop)
                .moddtShop(moddtShop)
                .ynDel(ynDel)
                .build();

        return shopInfoEntity;
    }

    @Builder
    public ShopInfoDto(Long seqShop, String nmShop, String addr1Shop, String addr2Shop, String noTelShop,
                       BigDecimal latitudeShop, BigDecimal longitudeShop, String commentShop, String writerShop,
                       LocalDateTime regdtShop, String modifierShop, LocalDateTime moddtShop, String ynDel) {
        this.seqShop = seqShop;
        this.nmShop = nmShop;
        this.addr1Shop = addr1Shop;
        this.addr2Shop = addr2Shop;
        this.noTelShop = noTelShop;
        this.latitudeShop = latitudeShop;
        this.longitudeShop = longitudeShop;
        this.commentShop = commentShop;
        this.writerShop = writerShop;
        this.regdtShop = regdtShop;
        this.modifierShop = modifierShop;
        this.moddtShop = moddtShop;
        this.ynDel = ynDel;
    }
}
