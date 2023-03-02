package com.epam.esm.dao.certificate;

import com.epam.esm.dao.SimpleDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface GiftCertificateDAO extends SimpleDAO<GiftCertificate, Integer> {
    List<GiftCertificate> getAll(String s);

    void addGiftCertificatesTags(List<Tag> tags, GiftCertificate created);

    Optional<GiftCertificate> findByName(String certificateName);
}
