package com.example.demo.controller;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Document;
import com.example.demo.request.CreateDocumentRequest;
import com.example.demo.service.DocumentService;
import com.example.demo.util.StringToUuidConverter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/document")
@Transactional
public class DocumentController {
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private DocumentService documentService;

    @GetMapping("/")
    public Page<DocumentDto> getAllDocument(Pageable pageable,
                                            @RequestParam("tu_khoa")String tuKhoa,
                                            @RequestParam("co_quan_ban_hanh_id")String cqbhId) {

        return documentService.getAllDocument(pageable, tuKhoa, cqbhId);
    }

    @GetMapping("/{id}")
    public DocumentDto getDocById(@PathVariable("id") String id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping("/")
    public DocumentDto createNewDocument(@RequestBody CreateDocumentRequest request) {

        modelMapper.addConverter(new StringToUuidConverter());
        Document document = modelMapper.map(request,Document.class);
        List<String> attachIds = request.getAttachIds();
        return documentService.createNewDocument(document, attachIds);
    }

    @PutMapping("/{id}")
    public DocumentDto updateDocument(@RequestBody CreateDocumentRequest request,
                                      @PathVariable("id") String id) {

        modelMapper.addConverter(new StringToUuidConverter());
        Document document = modelMapper.map(request,Document.class);
        document.setId(UUID.fromString(id));

        List<String> attachIds = request.getAttachIds();
        documentService.createNewDocument(document, attachIds);
        return documentService.getDocumentById(id);
    }

//    private Document mapRequestAndDocument(@RequestBody CreateDocumentRequest request) {
//        modelMapper.createTypeMap(CreateDocumentRequest.class,Document.class)
//                .addMappings(mapper -> mapper.map(src -> UUID.fromString(src.getSoVanBanId()), Document::setSoVanBanId))
//                .addMappings(mapper -> mapper.map(src -> UUID.fromString(src.getCoQuanBanHanhId()), Document::setCoQuanBanHanhId));
//
//        Document document = modelMapper.map(request,Document.class);
//        return document;
//    }

}
