package com.example.demo.repository;

import com.example.demo.model.Announcement;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AnnouncementRepository extends JpaRepository<Announcement,Long> {

    @Query(value = "SELECT * FROM anouncement a  WHERE a.status = 'active' ORDER BY a.id DESC LIMIT 1",nativeQuery = true)
    Announcement getLastAnnouncement();

    @Query(value = "SELECT * FROM anouncement a  WHERE a.status = 'active' ORDER BY a.id DESC",nativeQuery = true)
    Page<Announcement> getNewAnnouncements(Pageable pageable);

    
}
