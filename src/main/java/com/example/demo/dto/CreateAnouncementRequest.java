package com.example.demo.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateAnouncementRequest {
    public String title;
    public String content;
    public List<String> attachId;
}
