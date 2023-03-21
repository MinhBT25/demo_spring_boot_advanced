package com.example.demo.repository;

import com.example.demo.model.NhomSoVanBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NhomSoVanBanRepository extends JpaRepository<NhomSoVanBan, UUID> {

    @Query(value = "SELECT nsvb.name FROM nhom_so_van_ban nsvb WHERE nsvb.id = :id", nativeQuery = true)
    String getNameById(@Param("id") String id);
}
