package com.example.demo.repository;

import com.example.demo.model.Attachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttachmentRepository extends JpaRepository<Attachment,String> {
    @Modifying
    @Query(value = "SELECT a FROM Attachment a where a.id in :listId")
    public List<Attachment> findByListId(@Param("listId") List<String> listId);
}
