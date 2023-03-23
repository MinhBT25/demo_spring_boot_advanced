package com.example.demo.repository.impl;

import com.example.demo.model.Document;
import com.example.demo.model.QDocument;
import com.example.demo.repository.CustomDocumentRepository;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Repository
public class CustomDocumentReopitoryImpl implements CustomDocumentRepository{

    @PersistenceContext
    private EntityManager em;

    @Override
    public Page<Document> getAllDocument(Pageable pageable,
                                         String tuKhoa,String coQuanBanHanh) {
        QDocument d = QDocument.document;

        JPAQueryFactory jpaQueryFactory = new JPAQueryFactory(em);

        BooleanBuilder builder = new BooleanBuilder();
        if (tuKhoa != null) {
            builder.or(d.trichYeu.contains(tuKhoa))
                    .or(d.soHieu.contains(tuKhoa))
                    .or(d.soDen.contains(tuKhoa));
        }
        if (coQuanBanHanh != null && !coQuanBanHanh.isEmpty()) {
            builder.and(d.coQuanBanHanhId.eq(UUID.fromString(coQuanBanHanh)));
        }

        JPAQuery<Document> query = jpaQueryFactory.selectFrom(d).where(builder);

        QueryResults<Document> documentList =query.offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        Page<Document> documentPages = new PageImpl(documentList.getResults(),pageable,documentList.getTotal());
        return documentPages;
    }
}
