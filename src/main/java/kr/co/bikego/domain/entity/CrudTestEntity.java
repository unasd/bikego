package kr.co.bikego.domain.entity;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "CRUD_TEST", schema = "dbtweeksbike", catalog = "")
public class CrudTestEntity {
    private PopupinfoEntity popup;

    @OneToOne
    public PopupinfoEntity getPopup() {
        return popup;
    }

    public void setPopup(PopupinfoEntity popup) {
        this.popup = popup;
    }

    private int id;

    @Id
    @javax.persistence.Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private String contents;

    @Basic
    @Column(name = "contents")
    public String getContents() {
        return contents;
    }

    public void setContents(String contents) {
        this.contents = contents;
    }

    private String attachId;

    @Basic
    @Column(name = "attach_id")
    public String getAttachId() {
        return attachId;
    }

    public void setAttachId(String attachId) {
        this.attachId = attachId;
    }

    private String idWriter;

    @Basic
    @Column(name = "id_writer")
    public String getIdWriter() {
        return idWriter;
    }

    public void setIdWriter(String idWriter) {
        this.idWriter = idWriter;
    }

    private Timestamp dateWrite;

    @Basic
    @Column(name = "date_write")
    public Timestamp getDateWrite() {
        return dateWrite;
    }

    public void setDateWrite(Timestamp dateWrite) {
        this.dateWrite = dateWrite;
    }

    private String idModifier;

    @Basic
    @Column(name = "id_modifier")
    public String getIdModifier() {
        return idModifier;
    }

    public void setIdModifier(String idModifier) {
        this.idModifier = idModifier;
    }

    private Timestamp dateModify;

    @Basic
    @Column(name = "date_modify")
    public Timestamp getDateModify() {
        return dateModify;
    }

    public void setDateModify(Timestamp dateModify) {
        this.dateModify = dateModify;
    }

    private String title;

    @Basic
    @Column(name = "title")
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CrudTestEntity that = (CrudTestEntity) o;
        return id == that.id &&
                Objects.equals(contents, that.contents) &&
                Objects.equals(attachId, that.attachId) &&
                Objects.equals(idWriter, that.idWriter) &&
                Objects.equals(dateWrite, that.dateWrite) &&
                Objects.equals(idModifier, that.idModifier) &&
                Objects.equals(dateModify, that.dateModify) &&
                Objects.equals(title, that.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, contents, attachId, idWriter, dateWrite, idModifier, dateModify, title);
    }
}
