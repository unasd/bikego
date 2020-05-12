package kr.co.bikego.domain.entity;


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
public class PopupinfoEntity {






    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int popupSeq;

    @Column(length = 100, nullable = false, name = "popup_title") //네이밍 지정
    private String popupTitle;

    @Column(length = 100, nullable = true)
    private String popupWriter;

    @Column(length = 100, nullable = true)
    private String popupUrl;

    @Column(length = 20, nullable = true)
    private Timestamp popupRegdt;

    @Column(length = 20, nullable = true)
    private Timestamp popupModdt;

    @Column(length = 20, nullable = true)
    private String popYn;

    @Column(length = 20, nullable = true)
    private String delYn;

    @Column(length = 20, nullable = true)
    private String popupStartDt;

    @Column(length = 20, nullable = true)
    private String popupEndDt;

    @Column(length = 20, nullable = true)
    private String ipReg;

    @Column(length = 20, nullable = true)
    private String attachId;




    @Builder
    public PopupinfoEntity(int popupSeq,String popupTitle,String popupUrl,String popupWriter,Timestamp popupRegdt,Timestamp popupModdt,String popYn,String delYn,String popupStartDt,String popupEndDt,String ipReg,String attachId) {
        //this.id = id;
        this.popupSeq  =popupSeq;
        this.popupTitle=popupTitle;
        this.popupUrl  =popupUrl;
        this.popupWriter=popupWriter;
        this.popupRegdt=popupRegdt;
        this.popupModdt=popupModdt;
        this.popYn=popYn;
        this.delYn=delYn;
        this.popupStartDt=popupStartDt;
        this.popupEndDt=popupEndDt;
        this.ipReg=ipReg;
        this.attachId=attachId;

    }
}