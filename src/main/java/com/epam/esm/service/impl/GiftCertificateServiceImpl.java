package com.epam.esm.service.impl;

import com.epam.esm.dao.certificate.GiftCertificateDAO;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.impl.GiftCertificateDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ApplicationException;
import com.epam.esm.exception.ExceptionMessage;
import com.epam.esm.service.GiftCertificateService;
import com.epam.esm.service.TagService;
import com.epam.esm.service.util.QueryBuilder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@Slf4j
@Service
public class GiftCertificateServiceImpl implements GiftCertificateService {
    private final QueryBuilder builder;
    private final GiftCertificateDAO repository;

    private final GiftCertificateDTOMapper certificateMapper;
    private final TagService tagService;

    @Autowired
    public GiftCertificateServiceImpl(QueryBuilder builder, GiftCertificateDAO repository, GiftCertificateDTOMapper certificateMapper, TagService tagService) {
        this.builder = builder;
        this.repository = repository;
        this.certificateMapper = certificateMapper;
        this.tagService = tagService;
    }

    @Override
    public GiftCertificateDto getCertificateById(int id) {
        GiftCertificate certificate = repository.findById(id).orElseThrow(() -> {
            log.error(ExceptionMessage.CERTIFICATE_NOT_FOUND + "(" + id + ")");
            return new ApplicationException(ExceptionMessage.CERTIFICATE_NOT_FOUND);
        });
        return certificateMapper.toDTO(certificate);
    }

    @Override
    @Transactional
    public GiftCertificateDto createNewCertificate(GiftCertificateDto certificateDTO) {
        GiftCertificate certificate = certificateMapper.toEntity(certificateDTO);
        if (repository.findByName(certificate.getCertificateName()).isPresent()) {
            log.error(ExceptionMessage.CERTIFICATE_IS_ALREADY_EXISTS);
            throw new ApplicationException(ExceptionMessage.CERTIFICATE_IS_ALREADY_EXISTS);
        }
        setNewCreateDate(certificate);
        setNewLastUpdateDate(certificate);
        GiftCertificate createdCertificate = repository.create(certificate);
        List<Tag> tags = tagService.createNewTagsForCertificate(certificate.getTags());
        repository.addGiftCertificatesTags(tags, createdCertificate);
        createdCertificate.setTags(tags);
        return certificateMapper.toDTO(certificate);
    }

    private void setNewLastUpdateDate(GiftCertificate certificate) {
        certificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    private void setNewCreateDate(GiftCertificate certificate) {
        certificate.setCreateDate(Timestamp.valueOf(LocalDateTime.now()));
    }

    @Override
    public List<GiftCertificateDto> getCertificates(Map<String, String> params) {
        List<GiftCertificate> certificates = repository.getAll(builder.buildQuery(params));
        return certificates.stream().map(certificateMapper::toDTO).collect(Collectors.toList());
    }

    @Override
    public boolean deleteCertificateById(int id) {
        getCertificateById(id);
        return repository.delete(id);
    }

    @Override
    @Transactional
    public GiftCertificateDto updateCertificate(int id, GiftCertificateDto certificateDto) {
        log.info("ID:  " + id);
        GiftCertificate certificate = certificateMapper.toEntity(certificateDto);
        GiftCertificate current = repository.findById(id).orElseThrow(() -> {
            log.error(ExceptionMessage.CERTIFICATE_NOT_FOUND);
            return new ApplicationException(ExceptionMessage.CERTIFICATE_NOT_FOUND);
        });
        List<Tag> tagsFromDB = current.getTags();
        certificateMapper.merge(current, certificate);
        if (nonNull(current.getTags())) {
            List<Tag> newTags = current.getTags();
            List<Tag> addTags = tagService.createNewTagsForCertificate(newTags.stream()
                    .filter(tag -> !tagsFromDB.contains(tag))
                    .collect(Collectors.toList()));
            tagService.deleteAllTagsFromCertificate(tagsFromDB.stream()
                    .filter(tag -> !current.getTags().contains(tag))
                    .collect(Collectors.toList()), current);
            repository.addGiftCertificatesTags(addTags, current);//<--
        }
        return certificateMapper.toDTO(repository.update(current));
    }
}
