package com.example.demo.service.impl;

import com.example.demo.model.NhomSoVanBan;
import com.example.demo.repository.NhomSoVanBanRepository;
import com.example.demo.service.NhomSoVanBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class NhomSoVanBanServiceImpl implements NhomSoVanBanService {

    @Autowired
    private NhomSoVanBanRepository nhomSoVanBanRepository;

    @Override
    public Page<NhomSoVanBan> getAll(Pageable pageable) {
        return nhomSoVanBanRepository.findAll(pageable);
    }
}
