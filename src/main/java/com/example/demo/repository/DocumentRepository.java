package com.example.demo.repository;

import com.example.demo.model.Document;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;

import java.util.UUID;


public interface DocumentRepository extends JpaRepository<Document,UUID> {

}
