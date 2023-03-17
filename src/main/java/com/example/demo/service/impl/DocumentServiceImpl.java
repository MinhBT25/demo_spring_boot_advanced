package com.example.demo.service.impl;

import com.example.demo.dto.DocumentDto;
import com.example.demo.jwt.JwtTokenProvider;
import com.example.demo.model.Attachment;
import com.example.demo.model.DocAttachment;
import com.example.demo.model.Document;
import com.example.demo.model.LoadFile;
import com.example.demo.repository.AttachmentRepository;
import com.example.demo.repository.DocAttachRepository;
import com.example.demo.repository.DocumentRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.DocumentService;
import com.example.demo.service.FileService;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import com.mongodb.client.gridfs.model.GridFSFile;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.gridfs.GridFsOperations;
import org.springframework.data.mongodb.gridfs.GridFsTemplate;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class DocumentServiceImpl implements DocumentService {

    @Autowired
    private DocumentRepository documentRepository;

    @Autowired
    private AttachmentRepository attachRepository;

    @Autowired
    private DocAttachRepository docAttachRepository;

    @Autowired
    private FileService fileService;

    public Page<Document> getAllDocument(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    @Override
    public void createNewDocument(Document document,List<String> listAttachmentId){
        documentRepository.save(document);
        for (String attachId: listAttachmentId) {
            System.out.println("a1"+attachId);
            Attachment attachment = new Attachment();
            attachment.setId(attachId);
            attachment.setName(fileService.getFileNameById(attachId));
            attachRepository.save(attachment);

            DocAttachment docAttachment = new DocAttachment();
            docAttachment.setDocumentId(document.getId());
            docAttachment.setFileId(attachId);

            docAttachRepository.save(docAttachment);
        }
    }

    @Override
    public DocumentDto getDocumentById(long id) throws Exception {
        Document document = documentRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Document not found with id : " + id)
        );

        DocumentDto documentDto = new DocumentDto();
        List<String> listAttachId = docAttachRepository.findListAttachIdByDocId(id);
        if (listAttachId.size() ==0){
            throw new Exception("Không có tệp đính kèm");
        }
        //Chua check khong co tep dinh kem
        documentDto.setAttachmentNames(attachRepository.findByListId(listAttachId));
        documentDto.setId(document.getId());
        documentDto.setContent(document.getContent());
        documentDto.setTitle(document.getTitle());

        return documentDto;
    }
}
