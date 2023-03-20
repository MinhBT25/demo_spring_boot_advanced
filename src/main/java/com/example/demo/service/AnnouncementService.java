package com.example.demo.service;

import com.example.demo.dto.AnnouncementDto;
import com.example.demo.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface AnnouncementService {
    Page<Announcement> getAllAnnouncement(Pageable pageable);

    Announcement createNewAnnouncement(Announcement announcement, List<String> attachments) throws IOException;

    AnnouncementDto getAnnouncementById(long id) throws Exception;

    void updateAnnouncement(Announcement announcement);

    Announcement deleteAnnouncement(long id);

}
