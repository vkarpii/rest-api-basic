package com.epam.esm.dao.certificate.mapper;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GiftCertificateMapper implements RowMapper<GiftCertificate> {
    @Override
    public GiftCertificate mapRow(ResultSet resultSet, int i) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        List<Tag> tags = new ArrayList<>();
        int id = resultSet.getInt("id");
        int currentId = id;
        certificate.setId(id);
        certificate.setCertificateName(resultSet.getString("certificate_name"));
        certificate.setCertificateDescription(resultSet.getString("certificate_description"));
        certificate.setPrice(resultSet.getDouble("price"));
        certificate.setDuration(resultSet.getInt("duration"));
        certificate.setCreateDate(resultSet.getTimestamp("create_date"));
        certificate.setLastUpdateDate(resultSet.getTimestamp("last_update_date"));
        while (id == currentId) {
            tags.add(new Tag(
                    resultSet.getInt("id"),
                    resultSet.getString("tag_name")
            ));
            if (resultSet.next()) {
                currentId = resultSet.getInt("id");
            } else {
                break;
            }

        }
        resultSet.previous();
        certificate.setTags(tags);
        return certificate;
    }
}
