package com.example.demo.controller;

import com.example.demo.request.CreateAnnouncementRequest;
import com.example.demo.dto.AnnouncementDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Announcement;
import com.example.demo.model.Attachment;
import com.example.demo.model.LoadFile;
import com.example.demo.service.AnnouncementService;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/anouncement")
@Transactional
public class AnnouncementController {

    @Autowired
    private AnnouncementService announcementService;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @GetMapping("/")
    public Page<Announcement> getAllAnouncement(Pageable pageable) {
        return announcementService.getAllAnnouncement(pageable);
    }

    @PostMapping("/")
    public Announcement saveAnouncement(@RequestBody CreateAnnouncementRequest request) throws IOException {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Announcement announcement = new Announcement();
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setStatus("active");
        announcement.setUserName(userDetails.getUsername());

        List<String> listId = request.getAttachId();
        return announcementService.createNewAnnouncement(announcement, listId);

    }



    @GetMapping("/{id}")
    public AnnouncementDto getAnnouncementById(@PathVariable("id") long id) throws Exception {
        return announcementService.getAnnouncementById(id);
    }
    @PutMapping("/{id}")
    public AnnouncementDto updateAnnouncement(@RequestBody CreateAnnouncementRequest request,
                                             @PathVariable("id") long id) throws Exception {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Announcement announcement = new Announcement();
        announcement.setId(id);
        announcement.setTitle(request.getTitle());
        announcement.setContent(request.getContent());
        announcement.setUserName(userDetails.getUsername());
        announcement.setStatus("active");

        List<String> listId = request.getAttachId();
        announcementService.createNewAnnouncement(announcement,listId);
        return announcementService.getAnnouncementById(id);
    }

    @PostMapping("/delete/{id}")
    public void changeStatus(@PathVariable("id")Long id) throws Exception {
        AnnouncementDto announcementDto = announcementService.getAnnouncementById(id);
        Announcement announcement = new Announcement();
        announcement.setId(id);
    }
    @GetMapping("/download/{id}")
    public ResponseEntity<ByteArrayResource> download(@PathVariable String id) throws IOException {
        LoadFile loadFile = fileService.downloadFile(id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(loadFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + loadFile.getFilename() + "\"")
                .body(new ByteArrayResource(loadFile.getFile()));
    }

    @PostMapping("/upfile")
    public Attachment saveFile(MultipartFile file) throws IOException {
        return fileService.addFile(file);
    }
}
