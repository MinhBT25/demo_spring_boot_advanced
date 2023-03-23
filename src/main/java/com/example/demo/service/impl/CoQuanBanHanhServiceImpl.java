package com.example.demo.service.impl;

import com.example.demo.model.CoQuanBanHanh;
import com.example.demo.repository.CoQuanBanHanhRepository;
import com.example.demo.service.CoQuanBanHanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CoQuanBanHanhServiceImpl implements CoQuanBanHanhService {

    @Autowired
    private CoQuanBanHanhRepository coQuanBanHanhRepository;

    @Override
    public Page<CoQuanBanHanh> getAll(Pageable pageable) {
        return coQuanBanHanhRepository.findAll(pageable);
    }
}
