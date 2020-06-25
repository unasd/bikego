package kr.co.bikego.domain.entity;


import kr.co.bikego.domain.entity.TimeEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "popupinfo")
public class PopupinfoEntity extends TimeEntity {
/*
    @NamedNativeQuery(name = "popupinfo.findMainPopupList",query = "")
*/

/*
    @NamedNativeQuery(name = "popupinfo.findMainPopupList", query = "SELECT * FROM popupinfo e WHERE (e.popup_start_dt =< CURRENT_DATE AND  CURRENT_DATE < e.popup_end_dt) AND e.del_yn ='N' and e.pop_yn = 'Y'")
*/

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(length = 100, nullable = false, name = "popup_seq") //네이밍 지정
    private Long popupSeq;

    @Column(length = 100, nullable = false, name = "popup_title") //네이밍 지정
    private String popupTitle;

    @Column(length = 100, nullable = true, name="popup_writer")
    private String popupWriter;

    @Column(length = 100, nullable = true, name = "popup_url")
    private String popupUrl;

    @Column(length = 20, nullable = true, name = "popup_url_window")
    private String popupUrlWindow;

    @Column(length = 20, nullable = true, name = "pop_yn")
    private String popYn;

    @Column(length = 20, nullable = true, name = "del_yn")
    private String delYn;

    @Column(length = 20, nullable = true, name = "popup_start_dt")
    private String popupStartDt;

    @Column(length = 20, nullable = true, name = "popup_end_dt")
    private String popupEndDt;

    @Column(length = 20, nullable = true, name = "ip_reg")
    private String ipReg;

    @Column(length = 20, nullable = true, name = "attach_id")
    private String attachId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;






    @Builder
    public PopupinfoEntity(Long popupSeq,String popupTitle,String popupUrl,String getPopupUrlWindow,String popupWriter,String popYn,String delYn,String popupStartDt,String popupEndDt,String ipReg,String attachId,String contents) {
        //this.id = id;
        this.popupSeq  =popupSeq;
        this.popupTitle=popupTitle;
        this.popupUrl  =popupUrl;
        this.popupUrlWindow  =getPopupUrlWindow;
        this.popupWriter=popupWriter;
        this.popYn=popYn;
        this.delYn=delYn;
        this.popupStartDt=popupStartDt;
        this.popupEndDt=popupEndDt;
        this.ipReg=ipReg;
        this.attachId=attachId;
        this.contents=contents;

    }
}