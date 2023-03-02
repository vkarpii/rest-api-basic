package com.epam.esm.dao.tag.util;

public final class SQLQuery {
    public static final String CREATE_TAG = "INSERT INTO `tag`(`tag_name`) VALUES (?);";
    public static final String FIND_TAG_BY_ID = "SELECT DISTINCT * FROM Tag WHERE id=?";
    public static final String DELETE_TAG = "DELETE FROM Tag WHERE id=?";
    public static final String DELETE_TAG_FROM_CERTIFICATES = "DELETE FROM certificate_tag WHERE tag_id=?";
    public static final String TAG_IS_EXISTS = "SELECT * FROM Tag WHERE tag_name=?";
    public static final String FIND_ALL_TAGS = "SELECT * FROM Tag";
    public static final String DELETE_ALL_TAGS_FROM_CERTIFICATE = "DELETE certificate_tag FROM certificate_tag" +
            " JOIN tag " +
            "ON certificate_tag.tag_id=tag.id " +
            "WHERE certificate_tag.certificate_id=? AND tag.tag_name=?";
}
