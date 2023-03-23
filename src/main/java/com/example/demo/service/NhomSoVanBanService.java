package com.example.demo.service;

import com.example.demo.model.NhomSoVanBan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface NhomSoVanBanService {
    Page<NhomSoVanBan> getAll(Pageable pageable);
}
