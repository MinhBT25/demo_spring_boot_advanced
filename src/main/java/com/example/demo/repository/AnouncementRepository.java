package com.example.demo.repository;

import com.example.demo.model.Anouncement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnouncementRepository extends JpaRepository<Anouncement,Long> {
}
