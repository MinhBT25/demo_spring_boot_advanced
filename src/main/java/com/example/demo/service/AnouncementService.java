package com.example.demo.service;

import com.example.demo.dto.AnouncementDto;
import com.example.demo.model.Anouncement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface AnouncementService {
    Page<Anouncement> getAllDocument(Pageable pageable);

    void createNewDocument(Anouncement anouncement, List<String> attachments) throws IOException;

    AnouncementDto getDocumentById(long id) throws Exception;


}
