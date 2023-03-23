package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.util.List;

public interface CustomDocumentRepository{
    Page<Document> getAllDocument(Pageable pageable,
                                  String tuKhoa,String coQuanBanHanh);

}
