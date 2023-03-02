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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = {TestConfig.class})
public class TagDAOTest {
    @Qualifier("tagDAO")
    @Autowired
    private TagDAO tagDAO;

    @Qualifier("certificateDAO")
    @Autowired
    private GiftCertificateDAO certificateDAO;

    @Test
    void findByIdTest() {
        int expectedId = 1;
        Tag tag = tagDAO.findById(1).get();
        assertEquals(expectedId, tag.getId());
    }

    @Test
    void isExistTest() {
        Tag tag = tagDAO.isExist("Free").get();
        assertNotNull(tag);
    }

    @Test
    void createTagTest() {
        String expectedTagName = "Test";
        Tag create = new Tag(expectedTagName);
        Tag tag = tagDAO.create(create);
        assertEquals(expectedTagName, tag.getTagName());
    }

    @Test
    void getAllTest() {
        int expectedSize = 3;
        List<Tag> tags = tagDAO.getAll();
        assertEquals(expectedSize, tags.size());
    }

    @Test
    void deleteTagTest() {
        int id = tagDAO.create(new Tag("Delete this tag")).getId();
        tagDAO.delete(id);
        int expectedSize = 3;
        List<Tag> tags = tagDAO.getAll();
        assertEquals(expectedSize, tags.size());
    }

    @Test
    void updateTagTest() {
        assertThrows(UnsupportedOperationException.class, () -> {
            tagDAO.update(new Tag("test"));
        });
    }

    @Test
    void deleteFromCertificatesTest() {
        Tag tag = tagDAO.findById(1).get();
        tagDAO.deleteFromCertificates(tag.getId());

        GiftCertificate certificate = certificateDAO.findById(3).get();
        int expectedSize = 1;
        assertEquals(expectedSize, certificate.getTags().size());
    }
}
