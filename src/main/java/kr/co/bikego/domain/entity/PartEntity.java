package kr.co.bikego.domain.entity;


import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "partinfo")
public class PartEntity  {


    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(length = 100, nullable = false, name = "part_seq") //네이밍 지정
    private Long partSeq;

    @Column(length = 100, nullable = false, name = "part_title") //네이밍 지정
    private String partTitle;

    @Column(length = 100, nullable = true, name="part_writer")
    private String partWriter;

    @Column(length = 100, nullable = true, name = "part_url")
    private String partUrl;

    @Column(length = 20, nullable = true, name = "part_url_window")
    private String partUrlWindow;

    @Column(length = 20, nullable = true, name = "pop_yn")
    private String popYn;

    @Column(length = 20, nullable = true, name = "del_yn")
    private String delYn;

    @Column(length = 20, nullable = true, name = "part_start_dt")
    private String partStartDt;

    @Column(length = 20, nullable = true, name = "part_end_dt")
    private String partEndDt;

    @Column(length = 20, nullable = true, name = "ip_reg")
    private String ipReg;

    @Column(length = 20, nullable = true, name = "attach_id")
    private String attachId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String contents;

    @CreatedDate
    @Column(updatable = false ,name = "part_regdt")
    private LocalDateTime partRegdt;

    @LastModifiedDate
    @Column(updatable = false ,name = "part_moddt")
    private LocalDateTime partModdt;






    @Builder
    public PartEntity(Long partSeq, String partTitle, String partUrl, String getpartUrlWindow, String partWriter, String popYn, String delYn, String partStartDt, String partEndDt, String ipReg, String attachId, String contents,LocalDateTime partRegdt,LocalDateTime partModdt) {
        //this.id = id;
        this.partSeq  =partSeq;
        this.partTitle=partTitle;
        this.partUrl  =partUrl;
        this.partUrlWindow  =getpartUrlWindow;
        this.partWriter=partWriter;
        this.popYn=popYn;
        this.delYn=delYn;
        this.partStartDt=partStartDt;
        this.partEndDt=partEndDt;
        this.ipReg=ipReg;
        this.attachId=attachId;
        this.contents=contents;
        this.partRegdt=partRegdt;
        this.partModdt=partModdt;

    }
}