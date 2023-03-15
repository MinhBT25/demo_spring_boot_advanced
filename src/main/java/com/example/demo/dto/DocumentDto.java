package com.example.demo.dto;

import lombok.Data;

import java.util.List;

@Data
public class DocumentDto {
    private long id;
    private String title;
    private String content;
    private List<String> attachmentNames;
}
