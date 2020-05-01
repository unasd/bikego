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
    private String attachId;

    @Id
    private int attachFileSn;

    @Column(length = 300, nullable = false)
    private String attach_file_org_nm;

    @Column(length = 300, nullable = false)
    private String attach_file_srv_nm;

    @Column(length = 300, nullable = false)
    private String attach_file_path;

    @Column(nullable = false)
    private Long attach_file_size;

    @Column(length = 20, nullable = false)
    private String attach_extends;

    @Column(length = 1, nullable = false)
    private String del_yn;

    @Column(length = 20, nullable = false)
    private String attach_register;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime attach_regdt;

    @Column(length = 20, nullable = true)
    private String attach_modifier;

    @LastModifiedDate
    private LocalDateTime attach_moddt;

    @Builder
    public AttachEntity(String attachId, int attachFileSn, String attach_file_org_nm, String attach_file_srv_nm
                        , String attach_file_path, Long attach_file_size, String attach_extends
                        , String del_yn, String attach_register, LocalDateTime attach_regdt
                        , String attach_modifier, LocalDateTime attach_moddt) {
        this.attachId = attachId;
        this.attachFileSn = attachFileSn;
        this.attach_file_org_nm = attach_file_org_nm;
        this.attach_file_srv_nm = attach_file_srv_nm;
        this.attach_file_path = attach_file_path;
        this.attach_file_size = attach_file_size;
        this.attach_extends = attach_extends;
        this.del_yn = del_yn;
        this.attach_register = attach_register;
        this.attach_regdt = attach_regdt;
        this.attach_modifier = attach_modifier;
        this.attach_moddt = attach_moddt;
    }
}
