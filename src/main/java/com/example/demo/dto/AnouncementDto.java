package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class AnouncementDto {
    private long id;
    private String title;
    private String content;
    private List<String> attachmentNames;
}
