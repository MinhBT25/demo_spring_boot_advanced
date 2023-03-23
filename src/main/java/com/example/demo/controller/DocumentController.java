package com.example.demo.controller;

import com.example.demo.dto.DocumentDto;
import com.example.demo.model.Document;
import com.example.demo.request.CreateDocumentRequest;
import com.example.demo.request.SearchDocumentRequest;
import com.example.demo.service.DocumentService;
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
    private DocumentService documentService;

    @GetMapping("/")
    public Page<DocumentDto> getAllDocument(Pageable pageable, @RequestBody SearchDocumentRequest request) {

        return documentService.getAllDocument(pageable,request.getTuKhoa(),request.getCoQuanBanHanhId());
    }

    @GetMapping("/{id}")
    public DocumentDto getDocById(@PathVariable("id") String id) {
        return documentService.getDocumentById(id);
    }

    @PostMapping("/")
    public DocumentDto createNewDocument(@RequestBody CreateDocumentRequest request) {
        Document document = new Document();
        document.setSoVanBanId(UUID.fromString(request.getSoVanBanId()));
        document.setSoDen(request.getSoDen());
        document.setSoHieu(request.getSoHieu());
        document.setNgayDen(request.getNgayDen());
        document.setNgayVanBan(request.getNgayVanBan());
        document.setNguoiKi(request.getNguoiKi());
        document.setTrichYeu(request.getTrichYeu());
        document.setCoQuanBanHanhId(UUID.fromString(request.getCoQuanBanHanhId()));
        document.setDoKhan(request.getDoKhan());

        List<String> attachIds = request.getAttachIds();
        return documentService.createNewDocument(document,attachIds);
    }
    @PutMapping("/{id}")
    public DocumentDto updateDocument(@RequestBody CreateDocumentRequest request,
                                   @PathVariable("id") String id){
        Document document = new Document();
        document.setId(UUID.fromString(id));
        document.setSoVanBanId(UUID.fromString(request.getSoVanBanId()));
        document.setSoDen(request.getSoDen());
        document.setSoHieu(request.getSoHieu());
        document.setNgayDen(request.getNgayDen());
        document.setNgayVanBan(request.getNgayVanBan());
        document.setNguoiKi(request.getNguoiKi());
        document.setTrichYeu(request.getTrichYeu());
        document.setCoQuanBanHanhId(UUID.fromString(request.getCoQuanBanHanhId()));
        document.setDoKhan(request.getDoKhan());

        List<String> attachIds = request.getAttachIds();
        documentService.createNewDocument(document,attachIds);
        return documentService.getDocumentById(id);
    }

}
