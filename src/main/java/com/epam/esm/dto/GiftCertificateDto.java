package com.epam.esm.dto;

import lombok.Data;

import java.util.List;

@Data
public class GiftCertificateDto {//request без id / response
    private int id;
    private String name;
    private String description;
    private double price;
    private List<String> tags;
    private String createDate;
    private String lastUpdateDate;
    private int durationInDays;
}
