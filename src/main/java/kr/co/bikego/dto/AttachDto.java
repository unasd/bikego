package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.AttachId;
import kr.co.bikego.test.domain.entity.CrudEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class AttachDto {
    private String idAttach;
    private int snFileAttach;
    private String nmOrgFileAttach;
    private String nmSrvFileAttach;
    private String pathFileAttach;
    private Long sizeFileAttach;
    private String extendsAttach;
    private String ynDel;
    private String registerAttach;
    private LocalDateTime regdtAttach;
    private String modifierAttach;
    private LocalDateTime moddtAttach;

    public AttachEntity toEntity() {
        AttachEntity attachEntity = AttachEntity.builder()
                .idAttach(idAttach)
                .snFileAttach(snFileAttach)
                .nmOrgFileAttach(nmOrgFileAttach)
                .nmSrvFileAttach(nmSrvFileAttach)
                .pathFileAttach(pathFileAttach)
                .sizeFileAttach(sizeFileAttach)
                .extendsAttach(extendsAttach)
                .ynDel(ynDel)
                .registerAttach(registerAttach)
                .regdtAttach(regdtAttach)
                .modifierAttach(modifierAttach)
                .moddtAttach(moddtAttach)
                .build();
        return attachEntity;
    }

    @Builder
    public AttachDto(String idAttach, int snFileAttach, String nmOrgFileAttach, String nmSrvFileAttach
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
