package com.example.demo.model;

import lombok.Data;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.UUID;

@Entity
@Data
public class Document {
    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "id", columnDefinition = "binary(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID id;

    @Basic
    @Column(name = "so_van_ban_id", columnDefinition = "binary(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID soVanBanId;
    @Basic
    @Column(name = "so_hieu")
    private String soHieu;
    @Basic
    @Column(name = "so_den")
    private String soDen;
    @Basic
    @Column(name = "trich_yeu")
    private String trichYeu;
    @Basic
    @Column(name = "ngay_den")
    private String ngayDen;
    @Basic
    @Column(name = "ngay_van_ban")
    private String ngayVanBan;
    @Basic
    @Column(name = "nguoi_ki")
    private String nguoiKi;
    @Basic
    @Column(name = "do_khan")
    private String doKhan;
    @Basic
    @Column(name = "co_quan_ban_hanh_id", columnDefinition = "binary(36)")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private UUID coQuanBanHanhId;

}
