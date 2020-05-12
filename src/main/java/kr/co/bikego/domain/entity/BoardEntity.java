package kr.co.bikego.domain.entity;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@javax.persistence.Table(name = "board", schema = "dbtweeksbike", catalog = "")
public class BoardEntity {
    private int boardSeq;

    @Id
    @javax.persistence.Column(name = "board_seq")
    public int getBoardSeq() {
        return boardSeq;
    }

    public void setBoardSeq(int boardSeq) {
        this.boardSeq = boardSeq;
    }

    private String boardType;

    @Basic
    @javax.persistence.Column(name = "board_type")
    public String getBoardType() {
        return boardType;
    }

    public void setBoardType(String boardType) {
        this.boardType = boardType;
    }

    private String boardTitle;

    @Basic
    @javax.persistence.Column(name = "board_title")
    public String getBoardTitle() {
        return boardTitle;
    }

    public void setBoardTitle(String boardTitle) {
        this.boardTitle = boardTitle;
    }

    private String boardWriter;

    @Basic
    @javax.persistence.Column(name = "board_writer")
    public String getBoardWriter() {
        return boardWriter;
    }

    public void setBoardWriter(String boardWriter) {
        this.boardWriter = boardWriter;
    }

    private Timestamp boardRegdt;

    @Basic
    @javax.persistence.Column(name = "board_regdt")
    public Timestamp getBoardRegdt() {
        return boardRegdt;
    }

    public void setBoardRegdt(Timestamp boardRegdt) {
        this.boardRegdt = boardRegdt;
    }

    private Timestamp boardModdt;

    @Basic
    @javax.persistence.Column(name = "board_moddt")
    public Timestamp getBoardModdt() {
        return boardModdt;
    }

    public void setBoardModdt(Timestamp boardModdt) {
        this.boardModdt = boardModdt;
    }

    private String delYn;

    @Basic
    @javax.persistence.Column(name = "del_yn")
    public String getDelYn() {
        return delYn;
    }

    public void setDelYn(String delYn) {
        this.delYn = delYn;
    }

    private String ipReg;

    @Basic
    @javax.persistence.Column(name = "ip_reg")
    public String getIpReg() {
        return ipReg;
    }

    public void setIpReg(String ipReg) {
        this.ipReg = ipReg;
    }

    private String attachId;

    @Basic
    @javax.persistence.Column(name = "attach_id")
    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoardEntity that = (BoardEntity) o;
        return boardSeq == that.boardSeq &&
                Objects.equals(boardType, that.boardType) &&
                Objects.equals(boardTitle, that.boardTitle) &&
                Objects.equals(boardWriter, that.boardWriter) &&
                Objects.equals(boardRegdt, that.boardRegdt) &&
                Objects.equals(boardModdt, that.boardModdt) &&
                Objects.equals(delYn, that.delYn) &&
                Objects.equals(ipReg, that.ipReg) &&
                Objects.equals(attachId, that.attachId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(boardSeq, boardType, boardTitle, boardWriter, boardRegdt, boardModdt, delYn, ipReg, attachId);
    }
}
