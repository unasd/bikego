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
                .attachId(idAttach)
                .attachFileSn(snFileAttach)
                .attach_file_org_nm(nmOrgFileAttach)
                .attach_file_srv_nm(nmSrvFileAttach)
                .attach_file_path(pathFileAttach)
                .attach_file_size(sizeFileAttach)
                .attach_extends(extendsAttach)
                .del_yn(ynDel)
                .attach_register(registerAttach)
                .attach_regdt(regdtAttach)
                .attach_modifier(modifierAttach)
                .attach_moddt(moddtAttach)
                .build();
        return attachEntity;
    }

    @Builder
    public AttachDto(String attach_id, int attachFileSn, String attach_file_org_nm, String attach_file_srv_nm
                        , String attach_file_path, Long attach_file_size, String attach_extends
                        , String del_yn, String attach_register, LocalDateTime attach_regdt
                        , String attach_modifier, LocalDateTime attach_moddt) {
        this.idAttach = attach_id;
        this.snFileAttach = attachFileSn;
        this.nmOrgFileAttach = attach_file_org_nm;
        this.nmSrvFileAttach = attach_file_srv_nm;
        this.pathFileAttach = attach_file_path;
        this.sizeFileAttach = attach_file_size;
        this.extendsAttach = attach_extends;
        this.ynDel = del_yn;
        this.registerAttach = attach_register;
        this.regdtAttach = attach_regdt;
        this.modifierAttach = attach_modifier;
        this.moddtAttach = attach_moddt;
    }
}
