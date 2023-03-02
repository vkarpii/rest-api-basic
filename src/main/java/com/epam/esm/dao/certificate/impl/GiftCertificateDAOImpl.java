package com.epam.esm.dao.certificate.impl;

import com.epam.esm.dao.certificate.GiftCertificateDAO;
import com.epam.esm.dao.certificate.util.SQLQuery;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.dao.certificate.mapper.GiftCertificateMapper;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Component
public class GiftCertificateDAOImpl implements GiftCertificateDAO {
    private final JdbcTemplate jdbcTemplate;

    public GiftCertificateDAOImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public GiftCertificate create(GiftCertificate certificate) {
        KeyHolder key = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(SQLQuery.INSERT, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, certificate.getCertificateName());
            preparedStatement.setString(2, certificate.getCertificateDescription());
            preparedStatement.setDouble(3, certificate.getPrice());
            preparedStatement.setInt(4, certificate.getDuration());
            preparedStatement.setTimestamp(5, certificate.getCreateDate());
            preparedStatement.setTimestamp(6, certificate.getLastUpdateDate());
            return preparedStatement;
        }, key);
        certificate.setId(Objects.requireNonNull(key.getKey()).intValue());
        return certificate;
    }

    @Override
    public Optional<GiftCertificate> findById(Integer id) {
        return jdbcTemplate.query(connection ->
                                connection.prepareStatement(SQLQuery.FIND_BY_ID + id, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                        new GiftCertificateMapper())
                .stream().findAny();
    }

    @Override
    public boolean delete(Integer id) {
        jdbcTemplate.update(SQLQuery.DELETE_CERTIFICATE, id);
        return true;
    }

    @Override
    public GiftCertificate update(GiftCertificate certificate) {
        jdbcTemplate.update(SQLQuery.UPDATE,
                certificate.getCertificateName(),
                certificate.getCertificateDescription(),
                certificate.getPrice(),
                certificate.getDuration(),
                certificate.getLastUpdateDate(),
                certificate.getId());
        return certificate;
    }

    @Override
    public List<GiftCertificate> getAll(String condition) {
        return jdbcTemplate.query(connection ->
                        connection.prepareStatement(SQLQuery.GET_ALL + condition,
                                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY),
                new GiftCertificateMapper());
    }

    public void addGiftCertificatesTags(List<Tag> tags, GiftCertificate certificate) {
        tags.forEach(tag -> jdbcTemplate.update(connection -> {
            PreparedStatement statement = connection.prepareStatement(SQLQuery.INSERT_CERTIFICATE_TAGS);
            statement.setInt(1, certificate.getId());
            statement.setInt(2, tag.getId());
            return statement;
        }));
    }

    @Override
    public Optional<GiftCertificate> findByName(String certificateName) {
        return jdbcTemplate.query(SQLQuery.GET_CERTIFICATE_BY_NAME, new Object[]{certificateName}, new BeanPropertyRowMapper<>(GiftCertificate.class))
                .stream().findAny();
    }
}
