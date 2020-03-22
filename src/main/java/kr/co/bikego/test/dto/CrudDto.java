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
    private String id_attach;
    private String id_writer;
    private LocalDateTime date_write;
    private String id_modifier;
    private LocalDateTime date_modify;

    public CrudEntity toEntity() {
        CrudEntity crudEntity = CrudEntity.builder()
                .id(id)
                .title(title)
                .contents(contents)
                .id_attach(id_attach)
                .id_writer(id_writer)
                .id_modifier(id_modifier)
                .build();
        return crudEntity;
    }

    @Builder
    public CrudDto (Long id, String title, String contents, String id_attach, String id_writer, LocalDateTime date_write, String id_modifier, LocalDateTime date_modify) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.id_attach = id_attach;
        this.id_writer = id_writer;
        this.date_write = date_write;
        this.id_modifier = id_modifier;
        this.date_modify = date_modify;
    }
}
