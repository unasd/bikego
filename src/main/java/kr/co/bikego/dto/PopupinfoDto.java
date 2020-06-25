package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.AttachEntity;
import kr.co.bikego.domain.entity.PopupinfoEntity;
import kr.co.bikego.test.domain.entity.CrudEntity;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PopupinfoDto {

    private Long popupSeq;
    private String popupTitle;
    private String popupUrl;
    private String popupUrlWindow;
    private String popupWriter;
    private LocalDateTime popupRegdt;
    private LocalDateTime popupModdt;
    private String contents;
    private String popYn;
    private String delYn;
    private String popupStartDt;
    private String popupEndDt;
    private String ipReg;
    private String attachId;

    public PopupinfoEntity toEntity(){
        PopupinfoEntity popupinfoEntity = PopupinfoEntity.builder()
        .popupSeq(popupSeq)
        .popupTitle(popupTitle)
        .popupUrl(popupUrl)
        .getPopupUrlWindow(popupUrlWindow)
        .popupWriter(popupWriter)
        .popYn(popYn)
        .delYn(delYn)
        .popupStartDt(popupStartDt)
        .popupEndDt(popupEndDt)
        .ipReg(ipReg)
        .attachId(attachId)
        .contents(contents)
        .build();
        return popupinfoEntity;

    }

    @Builder
    public PopupinfoDto(Long popupSeq,String popupTitle,String popupUrl,String popupUrlWindow,String contents,String popupWriter,LocalDateTime popupRegdt,LocalDateTime popupModdt,String popYn,String delYn,String popupStartDt,String popupEndDt,String ipReg,String attachId) {
        this.popupSeq = popupSeq;
        this.popupTitle = popupTitle;
        this.popupUrl = popupUrl;
        this.popupUrlWindow = popupUrlWindow;
        this.popupWriter=popupWriter;
        this.popupRegdt = popupRegdt;
        this.popupModdt = popupModdt;
        this.popYn=popYn;
        this.delYn=delYn;
        this.popupStartDt=popupStartDt;
        this.popupEndDt=popupEndDt;
        this.ipReg=ipReg;
        this.attachId=attachId;
        this.contents=contents;
    }

}
