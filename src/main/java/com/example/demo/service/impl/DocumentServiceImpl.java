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
    public void createNewDocument(Document document, List<MultipartFile> files, String jwt) throws IOException {
        for (MultipartFile file: files) {
            //Save file
            DBObject metadata = new BasicDBObject();
            metadata.put("fileSize", file.getSize());
            Object fileID = template.store(file.getInputStream(), file.getOriginalFilename(), file.getContentType(), metadata);
            //Set id file to document
            Attachment attachment = new Attachment();
            attachment.setId(fileID.toString());
            attachment.setName(file.getOriginalFilename());
            attachmentRepository.save(attachment);

            //Set userId writes document
            long docId = tokenProvider.getUserIdFromJWT(jwt);
            document.setId(docId);
            //Save document
            documentRepository.save(document);

            DocAttachment docAttachment = new DocAttachment();
            docAttachment.setDocumentId(docId);
            docAttachment.setFileId(fileID.toString());
        }

    }

    @Override
    public DocumentDto getDocumentById(long id) {
        Document document = documentRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("Document not found with id : " + id)
        );

        DocumentDto documentDto = new DocumentDto();

        //Chua check khong co tep dinh kem
        documentDto.setAttachmentNames(attachmentRepository.findByDocumentId(docAttachRepository.findByDocumentId(id)));

        return documentDto;
    }


    public LoadFile downloadFile(String id) throws IOException {

        GridFSFile gridFSFile = template.findOne( new Query(Criteria.where("_id").is(id)) );

        LoadFile loadFile = new LoadFile();

        if (gridFSFile != null && gridFSFile.getMetadata() != null) {
            loadFile.setFilename( gridFSFile.getFilename() );

            loadFile.setFileType( gridFSFile.getMetadata().get("_contentType").toString() );

            loadFile.setFileSize( gridFSFile.getMetadata().get("fileSize").toString() );

            loadFile.setFile( IOUtils.toByteArray(operations.getResource(gridFSFile).getInputStream()) );
        }

        return loadFile;
    }
}
