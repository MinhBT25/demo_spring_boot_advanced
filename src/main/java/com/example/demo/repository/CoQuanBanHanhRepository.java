package com.example.demo.repository;

import com.example.demo.model.CoQuanBanHanh;
import com.example.demo.model.SoVanBan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface CoQuanBanHanhRepository extends JpaRepository<CoQuanBanHanh,UUID> {

    @Query(value = "SELECT cqbh.name FROM co_quan_ban_hanh cqbh WHERE cqbh.id = :id", nativeQuery = true)
    String getNameById(@Param("id") String id);
}
