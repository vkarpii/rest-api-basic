package com.epam.esm.dao;

import com.epam.esm.config.TestConfig;
import com.epam.esm.dao.certificate.GiftCertificateDAO;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Component
@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class GiftCertificateDAOTest {//NameIt //без Test
    @Qualifier("certificateDAO")
    @Autowired
    private GiftCertificateDAO certificateDAO;

    @Qualifier("tagDAO")
    @Autowired
    private TagDAO tagDAO;

    @Test
    void findByIdTest() {
        int expectedId = 1;

        GiftCertificate resultGiftCertificate = certificateDAO.findById(1).get();

        assertEquals(expectedId, resultGiftCertificate.getId());

        resultGiftCertificate = certificateDAO.findById(999999).orElse(null);
        assertNull(resultGiftCertificate);
    }

    @Test
    void findAllTest() {
        int expectedListSize = 3;

        List<GiftCertificate> resultGiftCertificateList = certificateDAO.getAll("");

        assertEquals(expectedListSize, resultGiftCertificateList.size());
    }

    @Test
    void createTest() {
        GiftCertificate giftCertificateForCreation = new GiftCertificate();
        giftCertificateForCreation.setCertificateName("Name");
        giftCertificateForCreation.setDuration(5);
        giftCertificateForCreation.setPrice(99.9);
        giftCertificateForCreation.setCreateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificateForCreation.setLastUpdateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificateForCreation.setCertificateDescription("Good");
        int expectedId = 4;

        GiftCertificate resultCreatedGiftCertificate = certificateDAO.create(giftCertificateForCreation);

        assertEquals(expectedId, resultCreatedGiftCertificate.getId());
    }

    @Test
    void updateTest() {
        GiftCertificate giftCertificateForUpdate = new GiftCertificate();
        giftCertificateForUpdate.setId(1);
        giftCertificateForUpdate.setCertificateName("newName");
        giftCertificateForUpdate.setDuration(5);
        giftCertificateForUpdate.setPrice(99.9);
        giftCertificateForUpdate.setCreateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificateForUpdate.setLastUpdateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificateForUpdate.setCertificateDescription("Good");
        String expectedName = "newName";

        GiftCertificate resultUpdatedGiftCertificate = certificateDAO.update(giftCertificateForUpdate);

        assertEquals(expectedName, resultUpdatedGiftCertificate.getCertificateName());
    }

    @Test
    void deleteTest() {
        assertEquals(Boolean.TRUE, certificateDAO.delete(1));
    }

    @Test
    void findByNameTest() {
        int expectedCertificateId = 1;

        GiftCertificate resultFoundByNameCertificate = certificateDAO.findByName("Spotify Basic").get();

        assertEquals(expectedCertificateId, resultFoundByNameCertificate.getId());

        resultFoundByNameCertificate = certificateDAO.findByName("Don`t exist").orElse(null);
        assertNull(resultFoundByNameCertificate);
    }

    @Test
    void addGiftCertificatesTagsTest() {
        GiftCertificate certificate = certificateDAO.findById(1).get();
        int expected = certificate.getTags().size() + 1;
        List<Tag> tags = new ArrayList<>();
        Tag tag = new Tag("Test Tag");
        tagDAO.create(tag);
        tags.add(tag);
        certificateDAO.addGiftCertificatesTags(tags, certificate);
        assertEquals(expected, certificateDAO.findById(1).get().getTags().size());
    }

}
