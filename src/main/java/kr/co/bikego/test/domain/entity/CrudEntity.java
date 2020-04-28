package kr.co.bikego.test.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "CRUD_TEST")
public class CrudEntity extends TimeEntity {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @Column(length = 30, nullable = true)
    private String attach_id;

    @Column(length = 20, nullable = false)
    private String id_writer;

    @Column(length = 20, nullable = true)
    private String id_modifier;

    @Builder
    public CrudEntity(Long id, String title, String contents, String attach_id, String id_writer, String id_modifier) {
        this.id = id;
        this.title = title;
        this.contents = contents;
        this.attach_id = attach_id;
        this.id_writer = id_writer;
        this.id_modifier = id_modifier;
    }
}