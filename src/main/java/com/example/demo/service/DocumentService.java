package com.example.demo.service;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Document;
import com.example.demo.model.LoadFile;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface DocumentService {
    Page<Document> getAllDocument(Pageable pageable);

    void createNewDocument(Document document, List<MultipartFile> files, String jwt) throws IOException;

    DocumentDto getDocumentById(long id);

    LoadFile downloadFile(String id) throws IOException;

//    void saveAttachment(Attachment attachment);
}
