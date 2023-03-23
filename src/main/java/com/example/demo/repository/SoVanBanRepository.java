package com.example.demo.repository;

import com.example.demo.model.SoVanBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SoVanBanRepository extends JpaRepository<SoVanBan,UUID> {

    @Query(value = "SELECT * FROM so_van_ban svb WHERE svb.id = :id",nativeQuery = true)
    SoVanBan getById(@Param("id") UUID id);

    @Query(value = "SELECT * FROM so_van_ban svb WHERE svb.nhom_so_van_ban_id = :nsvbId",nativeQuery = true)
    Page<SoVanBan> findBynhomSoVanBanId(@Param("nsvbId") String UUID, Pageable pageable);

}
