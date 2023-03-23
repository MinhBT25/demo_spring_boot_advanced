package com.example.demo.service;

import com.example.demo.model.CoQuanBanHanh;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CoQuanBanHanhService {
    Page<CoQuanBanHanh> getAll(Pageable pageable);
}
