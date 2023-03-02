package com.epam.esm.dao.tag.impl;

import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dao.tag.util.SQLQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class TagDAOImpl implements TagDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.CREATE_TAG,
                    Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, tag.getTagName());
            return preparedStatement;
        }, key);
        tag.setId(Objects.requireNonNull(key.getKey()).intValue());
        return tag;
    }

    @Override
    public Optional<Tag> findById(Integer id) { // Optional
        return jdbcTemplate.query(SQLQuery.FIND_TAG_BY_ID, new Object[]{id},
                        new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny();
    }

    @Override
    public boolean delete(Integer id) {
        jdbcTemplate.update(SQLQuery.DELETE_TAG, id);
        return true;
    }

    @Override
    public Tag update(Tag tag) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean deleteFromCertificates(int id) {
        jdbcTemplate.update(SQLQuery.DELETE_TAG_FROM_CERTIFICATES, id);
        return true;
    }

    @Override
    public Optional<Tag> isExist(String tagName) {
        return jdbcTemplate.query(SQLQuery.TAG_IS_EXISTS, new Object[]{tagName},
                        new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny();
    }

    @Override
    public List<Tag> getAll() {
        return jdbcTemplate.query(SQLQuery.FIND_ALL_TAGS,
                new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public boolean deleteAllTagsFromCertificate(List<Tag> tags, GiftCertificate certificate) {
        tags.forEach(tag -> {
            jdbcTemplate.update(SQLQuery.DELETE_ALL_TAGS_FROM_CERTIFICATE,
                    certificate.getId(), tag.getTagName());
        });
        return true;
    }
}
