package kr.co.bikego.domain.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
public class AttachId implements Serializable {
    private static final long serialVersionUID = 1L;

    @Column(name = "attach_id")
    private String idAttach;

    @Column(name = "attach_file_sn")
    private int snFileAttach;
}
