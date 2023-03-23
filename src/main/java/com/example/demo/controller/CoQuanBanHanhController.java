package com.example.demo.controller;

import com.example.demo.model.CoQuanBanHanh;
import com.example.demo.service.CoQuanBanHanhService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/co-qua-ban-hanh")
public class CoQuanBanHanhController {

    @Autowired
    private CoQuanBanHanhService coQuanBanHanhService;

    @GetMapping("/")
    public Page<CoQuanBanHanh> getAll(Pageable pageable) {
        return coQuanBanHanhService.getAll(pageable);
    }

}
