package kr.co.bikego.domain.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@DynamicUpdate
@Table(name = "noti_board")
public class NoticeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "noti_seq")
    private Long seqNoti;

    @Column(name = "noti_keyword", length = 100, nullable = false)
    private String keywordNoti;

    @Column(name = "noti_title", length = 100, nullable = false)
    private String titleNoti;

    @Column(name = "noti_contents", nullable = false)
    private String contentsNoti;

    @Column(name = "attach_id", length = 30, nullable = true)
    private String idAttach;

    @Column(name = "noti_writer", length = 20, nullable = false, updatable = false)
    private String writerNoti;

    @Column(name = "noti_regdt", nullable = false, updatable = false)
    private LocalDateTime regdtNoti;

    @Column(name = "noti_modifier", length = 20, nullable = true)
    private String modifierNoti;

    @Column(name = "noti_moddt", nullable = true)
    private LocalDateTime moddtNoti;

    @Column(name = "del_yn", length = 1, nullable = false)
    private String ynDel;


    @Builder
    public NoticeEntity(Long seqNoti, String keywordNoti, String titleNoti, String contentsNoti, String idAttach,
                        String writerNoti, LocalDateTime regdtNoti, String modifierNoti, LocalDateTime moddtNoti, String ynDel){
        this.seqNoti = seqNoti;
        this.keywordNoti = keywordNoti;
        this.titleNoti = titleNoti;
        this.contentsNoti = contentsNoti;
        this.idAttach = idAttach;
        this.writerNoti = writerNoti;
        this.regdtNoti = regdtNoti;
        this.modifierNoti = modifierNoti;
        this.moddtNoti = moddtNoti;
        this.ynDel = ynDel;
    }

}
