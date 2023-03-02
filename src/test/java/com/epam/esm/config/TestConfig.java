package com.epam.esm.config;

import com.epam.esm.dao.certificate.GiftCertificateDAO;
import com.epam.esm.dao.certificate.impl.GiftCertificateDAOImpl;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dao.tag.impl.TagDAOImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

@Configuration

public class TestConfig {

    private static final String SCRIPT_ENCODING = "UTF-8";

    @Bean
    public DataSource h2DataSource() {
        return new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setScriptEncoding(SCRIPT_ENCODING)
                .addDefaultScripts()
                .build();
    }

    @Bean
    public JdbcTemplate jdbcTemplate() {
        return new JdbcTemplate(h2DataSource());
    }

    @Bean
    public TagDAO tagDAO() {
        return new TagDAOImpl(jdbcTemplate());
    }

    @Bean
    public GiftCertificateDAO certificateDAO() {
        return new GiftCertificateDAOImpl(jdbcTemplate());
    }
}
