package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateDocumentRequest {
    public String title;
    public String content;
    public List<String> attachId;
}
