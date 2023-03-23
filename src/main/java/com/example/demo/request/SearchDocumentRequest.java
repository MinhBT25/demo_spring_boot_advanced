package com.example.demo.request;

import lombok.Getter;

import java.util.List;
@Getter
public class SearchDocumentRequest {
    public String tuKhoa;
    public String ngayDenStart;
    public String ngayDenEnd;
    public String ngayVanBanStart;
    public String ngayVanBanEnd;
    public String nguoiKi;
    public String coQuanBanHanhId;
}
