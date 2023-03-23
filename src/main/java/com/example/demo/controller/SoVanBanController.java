package com.example.demo.controller;

import com.example.demo.model.SoVanBan;
import com.example.demo.service.SoVanBanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/so-van-ban")
public class SoVanBanController {

    @Autowired
    private SoVanBanService soVanBanService;

    @GetMapping("/")
    public Page<SoVanBan> getAll(Pageable pageable) {
        return soVanBanService.getAll(pageable);
    }

    @GetMapping("/get-by-nhom-so/{id}")
    public Page<SoVanBan> getByNhomSoId(Pageable pageable,
                                        @PathVariable("id")String id) {
        return soVanBanService.getByNhomSoVanBanId(id,pageable);
    }
}
