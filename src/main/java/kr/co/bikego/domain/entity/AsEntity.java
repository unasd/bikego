package kr.co.bikego.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "as_board")
public class AsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "as_seq")
    private Long seqAs;

    @Column(length = 100, nullable = false, name = "as_title")
    private String titleAs;

    @Column(columnDefinition = "TEXT", nullable = false, name = "as_contents")
    private String contentsAs;

    @Column(length = 20, nullable = false, name = "as_tel_no")
    private String noTelAs;

    @Column(nullable = false, name = "as_latitude")
    private BigDecimal latitudeAs;

    @Column(nullable = false, name = "as_longitude")
    private BigDecimal longitudeAs;

    @Column(nullable = false, name= "as_location")
    private String locationAs;

    @Column(nullable = true, name = "as_location_detail")
    private String dtlLocationAs;

    @Column(length = 100, nullable = false, name = "as_password")
    private String passwordAs;

    @Column(length = 30, nullable = true, name = "attach_id")
    private String idAttach;

    @Column(length = 20, nullable = false, name = "as_writer")
    private String writerAs;

    @Column(nullable = true, name = "as_regdt")
    private LocalDateTime regdtAs;

    @Column(length = 20, nullable = true, name = "as_modifier")
    private String modifierAs;

    @Column(nullable = true, name = "as_moddt")
    private LocalDateTime moddtAs;

    @Builder
    public AsEntity(Long seqAs, String titleAs, String contentsAs, String noTelAs, BigDecimal latitudeAs, BigDecimal longitudeAs,
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
