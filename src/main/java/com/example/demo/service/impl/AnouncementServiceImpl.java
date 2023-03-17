package com.example.demo.service.impl;

import com.example.demo.dto.AnouncementDto;
import com.example.demo.model.Anouncement;
import com.example.demo.model.Attachment;
import com.example.demo.model.AnouncementAttachment;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.AnouncementAttachRepository;
import com.example.demo.repository.AnouncementRepository;
import com.example.demo.service.AnouncementService;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AnouncementServiceImpl implements AnouncementService {

    @Autowired
    private AnouncementRepository anouncementRepository;

    @Autowired
    private AttachmentRepository attachRepository;

    @Autowired
    private AnouncementAttachRepository anouncementAttachRepository;

    @Autowired
    private FileService fileService;

    public Page<Anouncement> getAllDocument(Pageable pageable) {
        return anouncementRepository.findAll(pageable);
    }

    @Override
    public void createNewDocument(Anouncement anouncement, List<String> listAttachmentId){
        anouncementRepository.save(anouncement);
        for (String attachId: listAttachmentId) {
            System.out.println("a1"+attachId);
            Attachment attachment = new Attachment();
            attachment.setId(attachId);
            attachment.setName(fileService.getFileNameById(attachId));
            attachRepository.save(attachment);

            AnouncementAttachment anouncementAttachment = new AnouncementAttachment();
            anouncementAttachment.setAnouncementId(anouncement.getId());
            anouncementAttachment.setFileId(attachId);

            anouncementAttachRepository.save(anouncementAttachment);
        }
    }

    @Override
    public AnouncementDto getDocumentById(long id) throws Exception {
        Anouncement anouncement = anouncementRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Anouncement not found with id : " + id)
        );

        AnouncementDto anouncementDto = new AnouncementDto();
        List<String> listAttachId = anouncementAttachRepository.findListAttachIdByDocId(id);
        if (listAttachId.size() ==0){
            throw new Exception("Không có tệp đính kèm");
        }
        //Chua check khong co tep dinh kem
        anouncementDto.setAttachmentNames(attachRepository.findByListId(listAttachId));
        anouncementDto.setId(anouncement.getId());
        anouncementDto.setContent(anouncement.getContent());
        anouncementDto.setTitle(anouncement.getTitle());

        return anouncementDto;
    }
}
