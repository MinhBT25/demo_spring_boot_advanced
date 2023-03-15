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
    private AttachmentRepository attachmentRepository;

    @Autowired
    private DocAttachRepository docAttachRepository;

    @Autowired
    private GridFsTemplate template;

    @Autowired
    private GridFsOperations operations;

    @Autowired
    private JwtTokenProvider tokenProvider;

    @Autowired
    private UserRepository userRepository;

    public Page<Document> getAllDocument(Pageable pageable) {
        return documentRepository.findAll(pageable);
    }

    @Override
    public void createNewDocument(Document document, Attachment attachment, String jwt) throws IOException {
            attachmentRepository.save(attachment);

            //Set userId writes document
            long docId = tokenProvider.getUserIdFromJWT(jwt);
            document.setId(docId);
            //Save document
            documentRepository.save(document);

            DocAttachment docAttachment = new DocAttachment();
            docAttachment.setDocumentId(docId);
            docAttachment.setFileId(attachment.getId());
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
        documentDto.setAttachmentNames(attachmentRepository.findByListId(listAttachId));
        documentDto.setId(document.getId());
        documentDto.setContent(document.getContent());
        documentDto.setTitle(document.getTitle());

        return documentDto;
    }
}
