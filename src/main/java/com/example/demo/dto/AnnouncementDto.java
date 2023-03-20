package com.example.demo.dto;

import com.example.demo.model.Attachment;
import lombok.Data;

import java.util.List;

@Data
public class AnnouncementDto {
    private long id;
    private String title;
    private String content;
    private List<Attachment> attachments;
    private String status;
}
