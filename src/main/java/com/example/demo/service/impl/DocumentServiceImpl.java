package com.example.demo.service.impl;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Attachment;
import com.example.demo.model.CoQuanBanHanh;
import com.example.demo.model.Document;
import com.example.demo.model.DocumentAttachment;
import com.example.demo.model.SoVanBan;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.CoQuanBanHanhRepository;
import com.example.demo.repository.DocumentAttachmentRepository;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.NhomSoVanBanRepository;
import com.example.demo.repository.SoVanBanRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class DocumentServiceImpl implements DocumentService {
    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private FileService fileService;

    @Autowired
    private AttachmentRepository attachRepository;

    @Autowired
    private DocumentAttachmentRepository documentAttachmentRepository;

    @Autowired
    private NhomSoVanBanRepository nhomSoVanBanRepository;

    @Autowired
    private SoVanBanRepository soVanBanRepository;

    @Autowired
    private CoQuanBanHanhRepository coQuanBanHanhRepository;

    @Override
    public Page<Document> getAllDocument(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    @Override
    public DocumentDto getDocumentById(String id) {
        Document document = documentRepository.getDocById(id);
        System.out.println("ID svb: " + document.getSoVanBanId().toString());

        DocumentDto documentDto = new DocumentDto();
        documentDto.setId(document.getId().toString());
        documentDto.setId(documentDto.getId());
        documentDto.setNgayDen(document.getNgayDen());
        documentDto.setNguoiKi(document.getNguoiKi());
        documentDto.setDoKhan(document.getDoKhan());
        documentDto.setSoDen(document.getSoDen());
        documentDto.setSoHieu(document.getSoHieu());
        documentDto.setTrichYeu(document.getTrichYeu());
        documentDto.setNgayVanBan(document.getNgayVanBan());
//        documentDto.setSoVanBan(soVanBanRepository.getNameById(document.getSoVanBanId().toString()));

        SoVanBan svb = soVanBanRepository.findById(document.getSoVanBanId()).orElseThrow(
                () -> new UsernameNotFoundException("Announcement not found with id : " + id)
        );
//        System.out.println(svb.getNhomSoVanBanId()+"id svb");

        documentDto.setSoVanBan(svb.getName());
        documentDto.setNhomSoVanBan(nhomSoVanBanRepository.getNameById(svb.getNhomSoVanBanId().toString()));
        documentDto.setCoQuanBanHanh(coQuanBanHanhRepository.getNameById(document.getCoQuanBanHanhId().toString()));

        List<String> listAttachId = documentAttachmentRepository.findListAttachIdByDocId(id);
        List<Attachment> attachments = new ArrayList<>();
        if (listAttachId.size() == 0) {
            attachments = null;
        } else {
            attachments = attachRepository.findByListId(listAttachId);
        }
        documentDto.setAttachments(attachments);

        return documentDto;
    }

    @Override
    public DocumentDto createNewDocument(Document document, List<String> attachIds) {
        documentRepository.save(document);
        for (String attachId : attachIds) {
            Attachment attachment = new Attachment();
            attachment.setId(attachId);
            attachment.setName(fileService.getFileNameById(attachId));
            attachRepository.save(attachment);

            DocumentAttachment documentAttachment = new DocumentAttachment();
            documentAttachment.setDocumentId(document.getId().toString());
            documentAttachment.setAttachmentId(attachId);

            documentAttachmentRepository.save(documentAttachment);

        }
        return getDocumentById(document.getId().toString());
    }


    @Override
    public Document updateDocument(Document document) {
        return null;
    }

//    @Override
//    public List<Document> searchByTrichYeu(String search) {
//
//        return documentRepository.searchByTrichYeu(search);
//    }


}
