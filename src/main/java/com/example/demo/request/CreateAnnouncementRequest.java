package com.example.demo.request;

import lombok.Getter;

import java.util.List;

@Getter
public class CreateAnnouncementRequest {
    public String title;
    public String content;
    public List<String> attachId;
}
