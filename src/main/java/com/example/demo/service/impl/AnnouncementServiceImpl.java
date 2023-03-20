package com.example.demo.service.impl;

import com.example.demo.dto.AnnouncementDto;
import com.example.demo.model.Announcement;
import com.example.demo.model.Attachment;
import com.example.demo.model.AnnouncementAttachment;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.AnnouncementAttachRepository;
import com.example.demo.repository.AnnouncementRepository;
import com.example.demo.service.AnnouncementService;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AnnouncementServiceImpl implements AnnouncementService {

    @Autowired
    private AnnouncementRepository announcementRepository;

    @Autowired
    private AttachmentRepository attachRepository;

    @Autowired
    private AnnouncementAttachRepository announcementAttachRepository;

    @Autowired
    private FileService fileService;

    public Page<Announcement> getAllAnnouncement(Pageable pageable) {
        return announcementRepository.getNewAnouncements(pageable);
    }

    @Override
    public Announcement createNewAnnouncement(Announcement announcement, List<String> listAttachmentId){
        announcementRepository.save(announcement);
        for (String attachId: listAttachmentId) {
            Attachment attachment = new Attachment();
            attachment.setId(attachId);
            attachment.setName(fileService.getFileNameById(attachId));
            attachRepository.save(attachment);

            AnnouncementAttachment announcementAttachment = new AnnouncementAttachment();
            announcementAttachment.setAnouncementId(announcement.getId());
            announcementAttachment.setFileId(attachId);

            announcementAttachRepository.save(announcementAttachment);
        }

        //Trả về đối tượng vừa tạo
        return announcementRepository.getLastAnouncement();
    }

    @Override
    public AnnouncementDto getAnnouncementById(long id) throws Exception {
        Announcement announcement = announcementRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Announcement not found with id : " + id)
        );
        //Chuyển dữ liệu model -> DTO
        AnnouncementDto announcementDto = new AnnouncementDto();
        List<String> listAttachId = announcementAttachRepository.findListAttachIdByDocId(id);
        List<Attachment> attachments = new ArrayList<>();
        if (listAttachId.size() ==0){
            attachments = null;
        }else {
            attachments = attachRepository.findByListId(listAttachId);
        }
        announcementDto.setAttachments(attachments);
        announcementDto.setId(announcement.getId());
        announcementDto.setContent(announcement.getContent());
        announcementDto.setTitle(announcement.getTitle());
        announcementDto.setStatus(announcement.getStatus());

        return announcementDto;
    }

    @Override
    public void updateAnnouncement(Announcement announcement) {
        announcementRepository.save(announcement);
    }

    @Override
    public Announcement deleteAnnouncement(long id) {
        Announcement announcement = announcementRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Announcement not found with id : " + id)
        );

            announcement.setStatus("deactivate");
        announcementRepository.save(announcement);
        return announcementRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Announcement not found with id : " + id)
        );
    }
}
