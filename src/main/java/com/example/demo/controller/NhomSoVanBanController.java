package com.example.demo.controller;

import com.example.demo.model.NhomSoVanBan;
import com.example.demo.service.NhomSoVanBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/nhom-so-van-ban")
public class NhomSoVanBanController {

    @Autowired
    private NhomSoVanBanService nhomSoVanBanService;

    @GetMapping("/")
    public Page<NhomSoVanBan> getAll(Pageable pageable) {
        return nhomSoVanBanService.getAll(pageable);
    }
}
