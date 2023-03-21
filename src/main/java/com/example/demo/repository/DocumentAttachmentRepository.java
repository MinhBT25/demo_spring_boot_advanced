package com.example.demo.repository;

import com.example.demo.model.DocumentAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentAttachmentRepository extends JpaRepository<DocumentAttachment, UUID> {

    @Query(value = "SELECT da.attachment_id FROM document_attachment da WHERE da.document_id= :docId",nativeQuery = true)
    List<String> findListAttachIdByDocId(@Param("docId") String id);
}
