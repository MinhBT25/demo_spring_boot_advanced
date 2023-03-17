package com.example.demo.controller;

import com.example.demo.dto.CreateAnouncementRequest;
import com.example.demo.dto.AnouncementDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Anouncement;
import com.example.demo.model.Attachment;
import com.example.demo.model.LoadFile;
import com.example.demo.service.AnouncementService;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/anouncement")
public class AnouncementController {

    @Autowired
    private AnouncementService anouncementService;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/get-all")
    public Page<Anouncement> getAllDocument(Pageable pageable) {
        return anouncementService.getAllDocument(pageable);
    }

    @PostMapping("/save")
    @Transactional
    public void saveDocument(@RequestBody CreateAnouncementRequest request) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Anouncement anouncement = new Anouncement();
        anouncement.setTitle(request.getTitle());
        anouncement.setContent(request.getContent());

        anouncement.setUserName(userDetails.getUsername());

        List<String> listId = request.getAttachId();
        anouncementService.createNewDocument(anouncement, listId);
    }

    @PostMapping("/upfile")
    public Attachment saveDocument(MultipartFile file) throws IOException {
        return fileService.addFile(file);
    }

    @GetMapping("/get-doc-by-id/{id}")
    public AnouncementDto getDocById(@PathVariable("id") long id) throws Exception {
        return anouncementService.getDocumentById(id);

    }

    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }
}
