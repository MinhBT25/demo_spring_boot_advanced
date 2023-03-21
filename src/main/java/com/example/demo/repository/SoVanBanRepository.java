package com.example.demo.repository;

import com.example.demo.model.SoVanBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface SoVanBanRepository extends JpaRepository<SoVanBan,UUID> {

//    @Query(value = "SELECT svb.name FROM so_van_ban svb WHERE svb.id = :id",nativeQuery = true)
//    String getNameById(@Param("id") String id);

    @Query(value = "SELECT * FROM so_van_ban svb WHERE svb.id = :id",nativeQuery = true)
    SoVanBan getById(@Param("id") UUID id);

}
