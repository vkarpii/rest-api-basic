package com.epam.esm.service;

import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.exception.ApplicationException;

import java.util.List;
import java.util.Map;

/**
 * This interface represents Service implementation that connected controller with Data Access Object.
 *
 * @param <T> has to implement {@link GiftCertificate} interface
 * @author Vitaly Karpii
 * @see GiftCertificate
 */
public interface GiftCertificateService {

    /**
     * This method return gift certificate by his id.
     *
     * @return {@link GiftCertificateDto}
     * @throws {@link ApplicationException} in case if tag not found with searched id.
     */
    public GiftCertificateDto getCertificateById(int id);

    /**
     * This method create new gift certificate.
     *
     * @throws {@link ApplicationException} in case if gift certificate's name is not correct.
     */
    public GiftCertificateDto createNewCertificate(GiftCertificateDto certificate);

    /**
     * This method return all active gift certificates with criteria.
     *
     * @return list of{@link GiftCertificateDto}
     */
    public List<GiftCertificateDto> getCertificates(Map<String, String> params);

    /**
     * This method delete gift certificate by his id.
     *
     * @throws {@link ApplicationException} in case if this gift certificate's id not found.
     */
    public boolean deleteCertificateById(int id);

    /**
     * This method update gift certificate.
     *
     * @throws {@link ApplicationException} in case if this gift certificate's id not found.
     */
    public GiftCertificateDto updateCertificate(int id, GiftCertificateDto certificate);
}
