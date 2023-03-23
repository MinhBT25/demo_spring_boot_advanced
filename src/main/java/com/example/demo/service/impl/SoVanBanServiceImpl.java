package com.example.demo.service.impl;

import com.example.demo.model.NhomSoVanBan;
import com.example.demo.model.SoVanBan;
import com.example.demo.repository.NhomSoVanBanRepository;
import com.example.demo.repository.SoVanBanRepository;
import com.example.demo.service.NhomSoVanBanService;
import com.example.demo.service.SoVanBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class SoVanBanServiceImpl implements SoVanBanService {

    @Autowired
    private SoVanBanRepository soVanBanRepository;

    @Override
    public Page<SoVanBan> getAll(Pageable pageable) {
        return soVanBanRepository.findAll(pageable);
    }

    @Override
    public Page<SoVanBan> getByNhomSoVanBanId(String id,Pageable pageable) {
        return soVanBanRepository.findBynhomSoVanBanId(id,pageable);
    }
}
