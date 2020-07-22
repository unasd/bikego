package kr.co.bikego.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "shopinfo")
public class ShopInfoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shop_seq")
    private Long seqShop;

    @Column(length = 100, nullable = false, name = "shop_nm")
    private String nmShop;

    @Column(length = 100, nullable = false, name = "shop_addr1")
    private String addr1Shop;

    @Column(length = 100, nullable = false, name = "shop_addr2")
    private String addr2Shop;

    @Column(length = 100, nullable = false, name = "shop_no_tel")
    private String noTelShop;

    @Column(nullable = false, name = "shop_latitude")
    private BigDecimal latitudeShop;

    @Column(nullable = false, name = "shop_longitude")
    private BigDecimal longitudeShop;

    @Column(nullable = true, name = "shop_comment")
    private String commentShop;

    @Column(length = 20, nullable = false, name = "shop_writer", updatable = false)
    private String writerShop;

    @Column(nullable = false, name = "shop_regdt", updatable = false)
    private LocalDateTime regdtShop;

    @Column(length = 20, nullable = true, name = "shop_modifier")
    private String modifierShop;

    @Column(nullable = true, name = "shop_moddt")
    private LocalDateTime moddtShop;

    @Column(length = 1, nullable = false, name = "del_yn")
    private String ynDel;

    @Builder
    public ShopInfoEntity(Long seqShop, String nmShop, String addr1Shop, String addr2Shop, String noTelShop,
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
