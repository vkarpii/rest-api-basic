package com.epam.esm.service;

import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ApplicationException;

import java.util.List;

/**
 * This interface represents Service implementation .
 *
 * @param <T> has to implement {@link Tag} interface
 * @author Vitaly Karpii
 * @see Tag
 */
public interface TagService {

    /**
     * This method create new tag.
     *
     * @throws {@link ApplicationException} in case if this tag's name already exist.
     */
    public Tag createNewTag(Tag tag);

    /**
     * This method delete tag by his id.
     *
     * @throws {@link ApplicationException} in case if this tag's id not found.
     */
    public boolean deleteTagById(int id);

    /**
     * This method return tag by his id.
     *
     * @return {@link Tag}
     * @throws {@link ApplicationException} in case if tag not found with searched id.
     */
    public Tag getTagById(int id);

    /**
     * This method return all existing tags.
     *
     * @return list of{@link Tag}
     */
    public List<Tag> getAllTags();

    /**
     * This method extracts existing tags or creates new ones.
     *
     * @return list of{@link Tag}
     */
    List<Tag> createNewTagsForCertificate(List<Tag> tags);

    /**
     * This method delete all tags for specific certificate.
     *
     * @return boolean
     */
    boolean deleteAllTagsFromCertificate(List<Tag> collect, GiftCertificate current);
}
