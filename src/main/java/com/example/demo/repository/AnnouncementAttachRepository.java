package com.example.demo.repository;

import com.example.demo.model.AnnouncementAttachment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AnnouncementAttachRepository extends JpaRepository<AnnouncementAttachment,Long> {

    @Modifying
    @Query(value = "SELECT aa.fileId FROM AnnouncementAttachment aa WHERE aa.anouncementId= :aId")
    public List<String> findListAttachIdByDocId(@Param("aId") long id);
}
