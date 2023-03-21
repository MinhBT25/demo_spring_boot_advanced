package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DocumentRepository extends JpaRepository<Document,UUID> {

    @Query(value = "SELECT * FROM document d ORDER BY d.id DESC LIMIT 1",nativeQuery = true)
    Document getLastDocument();

    @Query(value = "SELECT * FROM document d WHERE d.id = :id",nativeQuery = true)
    Document getDocById(@Param("id") String id);

//    @Query(value = "SELECT * FROM document d WHERE d.trich_yeu like %?trichYeu%",nativeQuery = true)
//    List<Document> searchByTrichYeu(@Param("trichYeu")String trichYeu);
}
