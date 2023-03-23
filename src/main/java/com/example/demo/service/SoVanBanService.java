package com.example.demo.service;

import com.example.demo.model.SoVanBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SoVanBanService {
    Page<SoVanBan> getAll(Pageable pageable);

    Page<SoVanBan> getByNhomSoVanBanId(String id,Pageable pageable);
}
