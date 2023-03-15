package com.example.demo.model;

import lombok.Data;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "doc_attachment", schema = "testingsystem", catalog = "")
@Data
public class DocAttachment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Basic
    @Column(name = "file_id")
    private String fileId;
    @Basic
    @Column(name = "document_id")
    private Long documentId;


}
