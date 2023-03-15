package com.example.demo.controller;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Attachment;
import com.example.demo.model.Document;
import com.example.demo.model.LoadFile;
import com.example.demo.service.DocumentService;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/document")
public class DocumentController {

    @Autowired
    private DocumentService documentService;

    @Autowired
    private FileService fileService;

    @GetMapping("/get-all")
    public Page<Document> getAllDocument(Pageable pageable){
        return documentService.getAllDocument( pageable);
    }
    @PostMapping("/save")
    public void saveDocument(Document document, Attachment attachment, String jwt) throws IOException {
//        jwt de lay ve id nguoi viet bai
        documentService.createNewDocument(document,attachment,jwt);
    }

    @PostMapping("/upfile")
    public Attachment saveDocument(MultipartFile file) throws IOException {
        return fileService.addFile(file);
    }

    @GetMapping("/get-doc-by-id/{id}")
    public DocumentDto getDocById(@PathVariable("id") long id) throws Exception {
        return documentService.getDocumentById(id);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType() ))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }
}
