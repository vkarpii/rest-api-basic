package com.epam.esm.dao.tag;

import com.epam.esm.dao.SimpleDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDAO extends SimpleDAO<Tag, Integer> {
    public boolean deleteFromCertificates(int id);

    public Optional<Tag> isExist(String tagName);

    public List<Tag> getAll();

    public boolean deleteAllTagsFromCertificate(List<Tag> oldTags, GiftCertificate current);
}
