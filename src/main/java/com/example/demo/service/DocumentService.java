package com.example.demo.service;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface DocumentService{
    Page<Document> getAllDocument(Pageable pageable);

    DocumentDto getDocumentById(String id);

    DocumentDto createNewDocument(Document document, List<String> attachIds);

    Document updateDocument(Document document);

//    List<Document> searchByTrichYeu(String search);
}
