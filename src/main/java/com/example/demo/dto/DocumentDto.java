package com.example.demo.dto;

import com.example.demo.model.Attachment;
import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

@Data
public class DocumentDto {
    private String id;
    private String nhomSoVanBan;
    private String soVanBan;
    private String soHieu;
    private String soDen;
    private String trichYeu;
    private String ngayDen;
    private String ngayVanBan;
    private String nguoiKi;
    private String doKhan;
    private String coQuanBanHanh;
    private List<Attachment> attachments;
}
