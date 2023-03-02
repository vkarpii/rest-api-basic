package com.epam.esm.dao.certificate.util;

public final class SQLQuery {
    public static final String FIND_BY_ID = "SELECT gift_certificate.*, tag.*" +
            "FROM gift_certificate " +
            " JOIN certificate_tag " +
            "ON certificate_tag.certificate_id=gift_certificate.id" +
            "  JOIN tag " +
            "ON tag.id=certificate_tag.tag_id " +
            "WHERE gift_certificate.id = ";
    public static final String DELETE_CERTIFICATE = "DELETE FROM gift_certificate WHERE id=?";
    public static final String GET_ALL = "SELECT gift_certificate.*, tag.*" +
            "FROM gift_certificate " +
            " JOIN certificate_tag " +
            "ON certificate_tag.certificate_id=gift_certificate.id" +
            "  JOIN tag " +
            "ON tag.id=certificate_tag.tag_id ";
    public static final String UPDATE = "UPDATE gift_certificate " +
            " SET " +
            "certificate_name = ?," +
            "certificate_description=?," +
            "price=?," +
            "duration=?," +
            "last_update_date=? " +
            " WHERE id=?";
    public static final String INSERT = "INSERT INTO `gift_certificate`" +
            "(`certificate_name`,`certificate_description`,`price`,`duration`,`create_date`,`last_update_date`) " +
            "VALUES (?,?,?,?,?,?);";
    public static final String INSERT_CERTIFICATE_TAGS = "INSERT INTO `certificate_tag` " +
            "(`certificate_id`,`tag_id`) VALUES(?,?)";
    public static final String GET_CERTIFICATE_BY_NAME = "SELECT DISTINCT * FROM gift_certificate WHERE certificate_name =?";
}
