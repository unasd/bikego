package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.NoticeEntity;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class NoticeDto {
    private Long seqNoti;
    private String keywordNoti;
    private String titleNoti;
    private String contentsNoti;
    private String idAttach;
    private String writerNoti;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime regdtNoti;
    private String modifierNoti;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime moddtNoti;
    private String ynDel;

    public NoticeEntity toEntity() {
        NoticeEntity noticeEntity = NoticeEntity.builder()
                .seqNoti(seqNoti)
                .keywordNoti(keywordNoti)
                .titleNoti(titleNoti)
                .contentsNoti(contentsNoti)
                .idAttach(idAttach)
                .writerNoti(writerNoti)
                .regdtNoti(regdtNoti)
                .modifierNoti(modifierNoti)
                .moddtNoti(moddtNoti)
                .ynDel(ynDel)
                .build();

        return noticeEntity;
    }

    @Builder
    public NoticeDto(Long seqNoti, String keywordNoti, String titleNoti, String contentsNoti, String idAttach,
                     String writerNoti, LocalDateTime regdtNoti, String modifierNoti, LocalDateTime moddtNoti, String ynDel) {
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
