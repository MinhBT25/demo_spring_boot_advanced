package com.example.demo.request;

import lombok.Data;
import lombok.Getter;

import java.util.List;

@Getter
public class CreateDocumentRequest {
    public String soVanBanId;
    public String soHieu;
    public String soDen;
    public String trichYeu;
    public String ngayDen;
    public String ngayVanBan;
    public String nguoiKi;
    public String doKhan;
    public String coQuanBanHanhId;
    public List<String> attachIds;
}
