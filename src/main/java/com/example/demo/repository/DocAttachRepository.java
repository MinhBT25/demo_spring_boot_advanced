package com.example.demo.repository;

import com.example.demo.model.DocAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DocAttachRepository extends JpaRepository<DocAttachment,Long> {

    @Modifying
    @Query(value = "SELECT da.fileId FROM DocAttachment da WHERE da.documentId = :docId")
    public List<Long> findByDocumentId(@Param("docId") long id);
}
