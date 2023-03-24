package com.example.demo.service.impl;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Attachment;
import com.example.demo.model.CoQuanBanHanh;
import com.example.demo.model.Document;
import com.example.demo.model.DocumentAttachment;
import com.example.demo.model.SoVanBan;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.CoQuanBanHanhRepository;
import com.example.demo.repository.CustomDocumentRepository;
import com.example.demo.repository.DocumentAttachmentRepository;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.NhomSoVanBanRepository;
import com.example.demo.repository.SoVanBanRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    @Autowired
    private CustomDocumentRepository customDocumentRepository;

    @Override
    public Page<DocumentDto> getAllDocument(Pageable pageable, String tuKhoa, String coQuanBanHanhId) {

        List<Document> documents = customDocumentRepository.getAllDocument(pageable, tuKhoa, coQuanBanHanhId).getContent();

        List<DocumentDto> documentDtos = documents.stream().map(document -> {

                    String id = document.getId().toString();
                    String soHieu =document.getSoHieu();
                    String soDen =document.getSoDen();
                    String trichYeu = document.getTrichYeu();
                    String ngayDen = document.getNgayDen();
                    String ngayVanBan = document.getNgayVanBan();
                    String nguoiKi = document.getNguoiKi();
                    String doKhan = document.getDoKhan();

                    SoVanBan svb = soVanBanRepository.findById(document.getSoVanBanId()).orElseThrow(
                            () -> new UsernameNotFoundException("So van ban not found")
                    );
                    String soVanBan = svb.getName();
                    String nhomSoVanBan = nhomSoVanBanRepository.getNameById(svb.getNhomSoVanBanId().toString());
                    String coQuanBanHanh = coQuanBanHanhRepository.getNameById(document.getCoQuanBanHanhId().toString());
                    List<String> listAttachId = documentAttachmentRepository.findListAttachIdByDocId(document.getId().toString());
                    List<Attachment> attachments = new ArrayList<>();
                    if (listAttachId.size() == 0) {
                        attachments = null;
                    } else {
                        attachments = attachRepository.findByListId(listAttachId);
                    }

                    return new DocumentDto(id,nhomSoVanBan,soVanBan,soHieu,soDen,trichYeu,ngayDen,ngayVanBan,nguoiKi,doKhan,coQuanBanHanh,attachments);
                })
                .collect(Collectors.toList());

        return new PageImpl<>(documentDtos, pageable, documents.size());
    }

    @Override
    public DocumentDto getDocumentById(String id) {
        Optional<Document> optional = documentRepository.findById(UUID.fromString(id));
        Document document = optional.get();

        DocumentDto documentDto = Stream.of(document).map(p -> {
            String soHieu =document.getSoHieu();
            String soDen =document.getSoDen();
            String trichYeu = document.getTrichYeu();
            String ngayDen = document.getNgayDen();
            String ngayVanBan = document.getNgayVanBan();
            String nguoiKi = document.getNguoiKi();
            String doKhan = document.getDoKhan();

            SoVanBan svb = soVanBanRepository.findById(document.getSoVanBanId()).orElseThrow(
                    () -> new UsernameNotFoundException("So van ban not found")
            );
            String soVanBan = svb.getName();
            String nhomSoVanBan = nhomSoVanBanRepository.getNameById(svb.getNhomSoVanBanId().toString());
            String coQuanBanHanh = coQuanBanHanhRepository.getNameById(document.getCoQuanBanHanhId().toString());
            List<String> listAttachId = documentAttachmentRepository.findListAttachIdByDocId(document.getId().toString());
            List<Attachment> attachments = new ArrayList<>();
            if (listAttachId.size() == 0) {
                attachments = null;
            } else {
                attachments = attachRepository.findByListId(listAttachId);
            }

            return new DocumentDto(id,nhomSoVanBan,soVanBan,soHieu,soDen,trichYeu,ngayDen,ngayVanBan,nguoiKi,doKhan,coQuanBanHanh,attachments);
        }).findFirst().orElse(null);


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

}
