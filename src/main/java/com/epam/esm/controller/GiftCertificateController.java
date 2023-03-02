package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.service.GiftCertificateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gift-certificate")
public class GiftCertificateController {
    private final GiftCertificateService service;

    @Autowired
    public GiftCertificateController(GiftCertificateService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public GiftCertificateDto find(@PathVariable int id) {
        return service.getCertificateById(id);
    }

    @GetMapping
    public List<GiftCertificateDto> findAll(@RequestParam Map<String, String> params) {
        return service.getCertificates(params);
    }

    @PostMapping
    public GiftCertificateDto create(@RequestBody GiftCertificateDto certificateDto) {
        return service.createNewCertificate(certificateDto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteCertificateById(id);
    }

    @PutMapping("/{id}")
    public GiftCertificateDto update(@PathVariable int id, @RequestBody GiftCertificateDto certificateDto) {
        return service.updateCertificate(id, certificateDto);
    }
}
