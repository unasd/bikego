package kr.co.bikego.test.dto;

import kr.co.bikego.test.domain.entity.CrudEntity;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class CrudDto {
    private Long id;
    private String title;
    private String contents;
    private String idAttach;
    private String writerId;
    private LocalDateTime writeDate;
    private String modifierId;
    private LocalDateTime modifyDate;

    public CrudEntity toEntity() {
        CrudEntity crudEntity = CrudEntity.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .attach_id(idAttach)
                .id_writer(writerId)
                .id_modifier(modifierId)
                .build();
        return crudEntity;
    }

    @Builder
    public CrudDto (Long id, String title, String contents, String idAttach, String id_writer, LocalDateTime date_write, String id_modifier, LocalDateTime date_modify) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.idAttach = idAttach;
        this.writerId = id_writer;
        this.writeDate = date_write;
        this.modifierId = id_modifier;
        this.modifyDate = date_modify;
    }
}
