package kr.co.bikego.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "attach_file")
@IdClass(AttachId.class)
public class AttachEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    private String idAttach;

    @Id
    private int snFileAttach;

    @Column(length = 300, nullable = false, name = "attach_file_org_nm")
    private String nmOrgFileAttach;

    @Column(length = 300, nullable = false, name = "attach_file_srv_nm")
    private String nmSrvFileAttach;

    @Column(length = 300, nullable = false, name = "attach_file_path")
    private String pathFileAttach;

    @Column(nullable = false, name = "attach_file_size")
    private Long sizeFileAttach;

    @Column(length = 20, nullable = false, name = "attach_extends")
    private String extendsAttach;

    @Column(length = 1, nullable = false, name = "del_yn")
    private String ynDel;

    @Column(length = 20, nullable = false, name = "attach_register")
    private String registerAttach;

    @CreatedDate
    @Column(updatable = false, name = "attach_regdt")
    private LocalDateTime regdtAttach;

    @Column(length = 20, nullable = true, name = "attach_modifier")
    private String modifierAttach;

    @LastModifiedDate
    @Column(name = "attach_moddt")
    private LocalDateTime moddtAttach;

    @Builder
    public AttachEntity(String idAttach, int snFileAttach, String nmOrgFileAttach, String nmSrvFileAttach
            , String pathFileAttach, Long sizeFileAttach, String extendsAttach
            , String ynDel, String registerAttach, LocalDateTime regdtAttach
            , String modifierAttach, LocalDateTime moddtAttach) {
        this.idAttach = idAttach;
        this.snFileAttach = snFileAttach;
        this.nmOrgFileAttach = nmOrgFileAttach;
        this.nmSrvFileAttach = nmSrvFileAttach;
        this.pathFileAttach = pathFileAttach;
        this.sizeFileAttach = sizeFileAttach;
        this.extendsAttach = extendsAttach;
        this.ynDel = ynDel;
        this.registerAttach = registerAttach;
        this.regdtAttach = regdtAttach;
        this.modifierAttach = modifierAttach;
        this.moddtAttach = moddtAttach;
    }
}
