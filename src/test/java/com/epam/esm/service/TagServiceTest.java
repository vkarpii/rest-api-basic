package com.epam.esm.service;

import com.epam.esm.dao.certificate.impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ApplicationException;
import com.epam.esm.service.impl.TagServiceImpl;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;


import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class TagServiceTest {

    @Mock
    private TagDAO tagDao;

    @Mock
    private GiftCertificateDAOImpl certificateDao;

    @InjectMocks
    private TagServiceImpl tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findTagByIdTest() {
        Tag tag = new Tag(1, "name");

        Mockito.when(tagDao.findById(1)).thenReturn(Optional.of(tag));
        Tag resultTag = tagService.getTagById(1);

        assertEquals(tag, resultTag);
    }

    @Test
    void findAllTagsTest() {
        Tag tag = new Tag("name");
        List<Tag> allTags = Collections.singletonList(tag);
        List<Tag> expectedTags = Collections.singletonList(tag);

        Mockito.when(tagDao.getAll()).thenReturn(allTags);
        List<Tag> resultTagList = tagService.getAllTags();

        assertEquals(expectedTags, resultTagList);
    }

    @Test
    void deleteTagTest() {
        Mockito.when(tagDao.delete(1)).thenReturn(Boolean.TRUE);
        Mockito.when(tagDao.findById(1)).thenReturn(Optional.of(new Tag()));
        Mockito.when(tagDao.deleteFromCertificates(1)).thenReturn(Boolean.TRUE);

        Assertions.assertEquals(Boolean.TRUE, tagService.deleteTagById(1));
    }

    @Test
    void createTagTest() {
        Tag tagForCreating = new Tag("name");
        Tag createdTag = new Tag(1, "name");

        Mockito.when(tagDao.create(tagForCreating)).thenReturn(createdTag);
        Tag resultTag = tagService.createNewTag(tagForCreating);

        assertEquals(createdTag, resultTag);
    }

    @Test
    void createTagTestShouldThrowException() {
        Tag tagForCreating = new Tag("name");
        Tag existingTag = new Tag(1, "name");

        Mockito.when(tagDao.isExist("name")).thenReturn(Optional.of(existingTag));

        assertThrows(ApplicationException.class, () -> tagService.createNewTag(tagForCreating));
    }

    @Test
    void deleteTagTestShouldThrowException() {
        int id = 99;
        assertThrows(ApplicationException.class, () -> tagService.deleteTagById(id));
    }

    @Test
    void createNewTagsForCertificateTest() {
        List<Tag> tags = Arrays.asList(new Tag("Tag1"));
        Mockito.when(tagDao.isExist(Mockito.anyString())).thenReturn(Optional.of(new Tag("Tag1")));
        Mockito.when(tagDao.create(Mockito.any())).thenReturn(new Tag("Tag1"));
        List<Tag> created = tagService.createNewTagsForCertificate(tags);
        assertEquals(tags.size(), created.size());
    }

    @Test
    void deleteAllTagsFromCertificateTest() {
        int expected = 2;
        List<Tag> tags = new ArrayList<>();
        tags.add(new Tag("Free"));
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(1);
        certificate.setTags(Arrays.asList(new Tag(""), new Tag("")));
        Mockito.when(tagDao.deleteAllTagsFromCertificate(tags, certificate)).thenReturn(true);
        tagService.deleteAllTagsFromCertificate(tags, certificate);
        Mockito.when(certificateDao.findById(certificate.getId())).thenReturn(Optional.of(certificate));
        certificate = certificateDao.findById(certificate.getId()).get();

        assertEquals(expected, certificate.getTags().size());
    }
}
