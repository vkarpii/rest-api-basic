package com.epam.esm.dto.mapper.impl;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.DTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;
import static java.util.Objects.requireNonNullElse;

@Component
public class GiftCertificateDTOMapper implements DTOMapper<GiftCertificateDto, GiftCertificate> {

    private static final String TIMEZONE = "UTC";
    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";

    private String convertTimesTampToISOFormat(Timestamp timestamp) {
        TimeZone timeZone = TimeZone.getTimeZone(TIMEZONE);
        DateFormat dateFormat = new SimpleDateFormat(DATE_FORMAT);
        dateFormat.setTimeZone(timeZone);
        return dateFormat.format(timestamp);
    }

    @Override
    public GiftCertificateDto toDTO(GiftCertificate certificate) {
        GiftCertificateDto giftCertificateDto = new GiftCertificateDto();
        giftCertificateDto.setId(certificate.getId());
        giftCertificateDto.setName(certificate.getCertificateName());
        giftCertificateDto.setDescription(certificate.getCertificateDescription());
        giftCertificateDto.setPrice(certificate.getPrice());
        if (nonNull(certificate.getTags())) {
            giftCertificateDto.setTags(certificate.getTags().stream().map(Tag::getTagName).collect(Collectors.toList()));
        }
        giftCertificateDto.setCreateDate(convertTimesTampToISOFormat(certificate.getCreateDate()));
        giftCertificateDto.setLastUpdateDate(convertTimesTampToISOFormat(certificate.getLastUpdateDate()));
        giftCertificateDto.setDurationInDays(certificate.getDuration());
        return giftCertificateDto;
    }

    @Override
    public GiftCertificate toEntity(GiftCertificateDto certificateDto) {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(certificateDto.getId());
        giftCertificate.setCertificateName(certificateDto.getName());
        giftCertificate.setCertificateDescription(certificateDto.getDescription());
        giftCertificate.setPrice(certificateDto.getPrice());
        if (nonNull(certificateDto.getTags())) {
            giftCertificate.setTags(certificateDto.getTags().stream().map(Tag::new).collect
                    (Collectors.toList()));
        }
        giftCertificate.setDuration(certificateDto.getDurationInDays());
        return giftCertificate;
    }

    public void merge(GiftCertificate mergedCertificate, GiftCertificate certificate) {
        mergedCertificate.setCertificateName(requireNonNullElse(certificate.getCertificateName(),
                mergedCertificate.getCertificateName()));
        mergedCertificate.setCertificateDescription(requireNonNullElse(certificate.getCertificateName(),
                mergedCertificate.getCertificateDescription()));
        mergedCertificate.setPrice(requireNonNullElse(certificate.getPrice(), mergedCertificate.getPrice()));
        mergedCertificate.setDuration(requireNonNullElse(certificate.getDuration(), mergedCertificate.getDuration()));
        mergedCertificate.setCreateDate(requireNonNullElse(certificate.getCreateDate(),
                mergedCertificate.getCreateDate()));
        mergedCertificate.setLastUpdateDate(Timestamp.valueOf(LocalDateTime.now()));
        mergedCertificate.setTags(requireNonNullElse(certificate.getTags(), mergedCertificate.getTags()));
    }
}

