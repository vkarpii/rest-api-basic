package com.epam.esm.service.impl;

import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ApplicationException;
import com.epam.esm.exception.ExceptionMessage;
import com.epam.esm.service.TagService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class TagServiceImpl implements TagService {

    private final TagDAO repository;

    @Autowired
    public TagServiceImpl(TagDAO repository) {
        this.repository = repository;
    }

    @Override
    public List<Tag> getAllTags() {
        return repository.getAll();
    }

    @Override
    public List<Tag> createNewTagsForCertificate(List<Tag> tags) {
        List<Tag> returned = new ArrayList<>();
        tags.forEach(element -> {
            Tag tag = element;
            tag = repository.isExist(tag.getTagName())
                    .orElseGet(() -> repository.create(element));
            returned.add(tag);
        });
        return returned;
    }

    @Override
    public boolean deleteAllTagsFromCertificate(List<Tag> collect, GiftCertificate current) {
        repository.deleteAllTagsFromCertificate(collect, current);
        return true;
    }

    @Override
    public Tag createNewTag(Tag tag) {
        if (repository.isExist(tag.getTagName()).isPresent()) {
            log.error(ExceptionMessage.TAG_IS_ALREADY_EXISTS);
            throw new ApplicationException(ExceptionMessage.TAG_IS_ALREADY_EXISTS);
        }
        return repository.create(tag);
    }

    @Override
    public boolean deleteTagById(int id) {
        getTagById(id);
        return repository.delete(id) && repository.deleteFromCertificates(id);
    }

    @Override
    public Tag getTagById(int id) {
        return repository.findById(id).orElseThrow(() -> {
            log.error(ExceptionMessage.TAG_NOT_FOUND);
            return new ApplicationException(ExceptionMessage.TAG_NOT_FOUND);
        });
    }
}
