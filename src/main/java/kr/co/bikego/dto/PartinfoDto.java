package kr.co.bikego.dto;

import kr.co.bikego.domain.entity.PartEntity;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class PartinfoDto {

    private Long partSeq;
    private String partTitle;
    private String partUrl;
    private String partUrlWindow;
    private String partWriter;
    private LocalDateTime partRegdt;
    private LocalDateTime partModdt;
    private String contents;
    private String popYn;
    private String delYn;
    private String partStartDt;
    private String partEndDt;
    private String ipReg;
    private String attachId;

    public PartEntity toEntity(){
        PartEntity partinfoEntity = PartEntity.builder()
        .partSeq(partSeq)
        .partTitle(partTitle)
        .partUrl(partUrl)
        .getpartUrlWindow(partUrlWindow)
        .partWriter(partWriter)
        .popYn(popYn)
        .delYn(delYn)
        .partStartDt(partStartDt)
        .partEndDt(partEndDt)
        .ipReg(ipReg)
        .attachId(attachId)
        .contents(contents)
        .partRegdt(partRegdt)
        .partModdt(partModdt)
        .build();
        return partinfoEntity;

    }

    @Builder
    public PartinfoDto(Long partSeq, String partTitle, String partUrl, String partUrlWindow, String contents, String partWriter, LocalDateTime partRegdt, LocalDateTime partModdt, String popYn, String delYn, String partStartDt, String partEndDt, String ipReg, String attachId) {
        this.partSeq = partSeq;
        this.partTitle = partTitle;
        this.partUrl = partUrl;
        this.partUrlWindow = partUrlWindow;
        this.partWriter=partWriter;
        this.partRegdt = partRegdt;
        this.partModdt = partModdt;
        this.popYn=popYn;
        this.delYn=delYn;
        this.partStartDt=partStartDt;
        this.partEndDt=partEndDt;
        this.ipReg=ipReg;
        this.attachId=attachId;
        this.contents=contents;
    }

}
