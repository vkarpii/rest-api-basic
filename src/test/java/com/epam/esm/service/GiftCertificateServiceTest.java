package com.epam.esm.service;

import com.epam.esm.dao.certificate.GiftCertificateDAO;
import com.epam.esm.dao.tag.TagDAO;
import com.epam.esm.dto.GiftCertificateDto;
import com.epam.esm.dto.mapper.impl.GiftCertificateDTOMapper;
import com.epam.esm.entity.GiftCertificate;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.ApplicationException;
import com.epam.esm.service.impl.GiftCertificateServiceImpl;
import com.epam.esm.service.util.QueryBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.sql.Timestamp;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class GiftCertificateServiceTest {

    @Mock
    private GiftCertificateDAO giftCertificateDao;

    @Mock
    private TagDAO tagDAO;

    @Mock
    private GiftCertificateDTOMapper mapper;

    @Spy
    private QueryBuilder builder =
            new QueryBuilder();

    @InjectMocks
    private GiftCertificateServiceImpl giftCertificateService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findGiftCertificateByIdTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setLastUpdateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificate.setCreateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        GiftCertificateDto returnedDto = new GiftCertificateDto();
        returnedDto.setId(1);
        returnedDto.setLastUpdateDate("2022-10-10 13:02:11.0");
        returnedDto.setCreateDate("2022-10-10 13:02:11.0");
        returnedDto.setTags(Collections.emptyList());

        Mockito.when(mapper.toDTO(giftCertificate)).thenReturn(returnedDto);
        Mockito.when(giftCertificateDao.findById(1)).thenReturn(Optional.of(giftCertificate));
        GiftCertificateDto resultDto = giftCertificateService.getCertificateById(1);

        assertEquals(returnedDto, resultDto);
    }

    @Test
    void deleteGiftCertificateTest() {
        Mockito.when(giftCertificateDao.delete(1)).thenReturn(Boolean.TRUE);
        Mockito.when(giftCertificateDao.findById(1)).thenReturn(Optional.of(new GiftCertificate()));
        Mockito.when(giftCertificateDao.delete(1)).thenReturn(Boolean.TRUE);

        assertEquals(Boolean.TRUE, giftCertificateService.deleteCertificateById(1));
    }

    @Test
    void getCertificatesTest() {
        List<GiftCertificate> giftCertificateList = new ArrayList<>();
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setLastUpdateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificate.setCreateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificateList.add(giftCertificate);
        giftCertificateList.add(giftCertificate);
        String searchQuery = "";

        Mockito.when(giftCertificateDao.getAll(searchQuery)).thenReturn(giftCertificateList);
        List<GiftCertificateDto> resultGiftCertificateDtoList = giftCertificateService.getCertificates(Mockito.anyMap());

        assertEquals(2, resultGiftCertificateDtoList.size());
    }

    @Test
    void createNewCertificateTest() {
        GiftCertificate giftCertificate = new GiftCertificate();
        giftCertificate.setId(1);
        giftCertificate.setCertificateName("Tested");
        giftCertificate.setLastUpdateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificate.setCreateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        giftCertificate.setTags(Arrays.asList(new Tag("Som1")));
        GiftCertificateDto dtoToAdd = new GiftCertificateDto();
        GiftCertificateDto returnedGiftCertificateDto = new GiftCertificateDto();
        returnedGiftCertificateDto.setId(1);
        returnedGiftCertificateDto.setName("Tested");
        returnedGiftCertificateDto.setLastUpdateDate("2022-10-10 13:02:11.0");
        returnedGiftCertificateDto.setCreateDate("2022-10-10 13:02:11.0");
        returnedGiftCertificateDto.setTags(Arrays.asList("Som1"));

        Mockito.when(mapper.toEntity(dtoToAdd)).thenReturn(giftCertificate);
        Mockito.when(mapper.toDTO(giftCertificate)).thenReturn(returnedGiftCertificateDto);
        Mockito.when(tagDAO.isExist("Som1")).thenReturn(Optional.of(new Tag("Som1")));
        Mockito.when(giftCertificateDao.create(Mockito.any())).thenReturn(giftCertificate);
        GiftCertificateDto resultGiftCertificateDto = giftCertificateService.createNewCertificate(dtoToAdd);

        assertEquals(returnedGiftCertificateDto, resultGiftCertificateDto);
    }

    @Test
    void updateGiftCertificateTest() {
        GiftCertificateDto giftCertificateDtoForUpdate = new GiftCertificateDto();
        giftCertificateDtoForUpdate.setId(1);
        giftCertificateDtoForUpdate.setName("Name");
        giftCertificateDtoForUpdate.setTags(Arrays.asList("Some Tag"));
        GiftCertificateDto returnedGiftCertificateDto = new GiftCertificateDto();
        returnedGiftCertificateDto.setName("Name");
        GiftCertificate updatedGiftCertificate = new GiftCertificate();
        updatedGiftCertificate.setCertificateName("Name");
        updatedGiftCertificate.setId(1);
        updatedGiftCertificate.setCreateDate(Timestamp.valueOf("2022-10-10 13:02:11.0"));
        updatedGiftCertificate.setTags(Arrays.asList(new Tag("Some Tag")));
        GiftCertificateDto updatedGifCertificateDto = new GiftCertificateDto();
        updatedGifCertificateDto.setId(1);
        updatedGifCertificateDto.setName("Name");
        updatedGifCertificateDto.setLastUpdateDate("2022-10-10 13:02:11.0");

        Mockito.when(giftCertificateDao.update(Mockito.any())).thenReturn(updatedGiftCertificate);
        Mockito.when(mapper.toEntity(giftCertificateDtoForUpdate)).thenReturn(updatedGiftCertificate);
        Mockito.when(mapper.toDTO(updatedGiftCertificate)).thenReturn(updatedGifCertificateDto);
        Mockito.when(giftCertificateDao.findById(1)).thenReturn(Optional.of(updatedGiftCertificate));
        GiftCertificateDto resultGifCertificateDto = giftCertificateService.updateCertificate
                (giftCertificateDtoForUpdate.getId(), giftCertificateDtoForUpdate);

        assertEquals(giftCertificateDtoForUpdate.getName(), resultGifCertificateDto.getName());
        assertNotNull(resultGifCertificateDto.getLastUpdateDate());
    }

    @Test
    void createGiftGiftCertificateTestShouldThrowException() {
        GiftCertificateDto giftCertificateDtoToAdd = new GiftCertificateDto();
        giftCertificateDtoToAdd.setName("Name");
        GiftCertificate existingGiftCertificate = new GiftCertificate();
        existingGiftCertificate.setCertificateName("Name");

        Mockito.when(mapper.toEntity(giftCertificateDtoToAdd)).thenReturn(existingGiftCertificate);
        Mockito.when(giftCertificateDao.findByName("Name")).thenReturn(Optional.of(new GiftCertificate()));

        assertThrows(ApplicationException.class, () -> giftCertificateService.createNewCertificate(giftCertificateDtoToAdd));
    }

    @Test
    void deleteGiftGiftCertificateTestShouldThrowException() {
        int id = 99;
        assertThrows(ApplicationException.class, () -> giftCertificateService.deleteCertificateById(id));
    }

}

