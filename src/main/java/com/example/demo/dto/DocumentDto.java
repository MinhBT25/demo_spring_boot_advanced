package com.example.demo.dto;

import com.example.demo.model.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Basic;
import javax.persistence.Column;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
