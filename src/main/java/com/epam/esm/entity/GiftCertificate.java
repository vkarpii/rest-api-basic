package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class GiftCertificate {
    private int id;
    private String certificateName;
    private String certificateDescription;
    private double price; //BigDecimal
    private int duration;
    private Timestamp createDate;
    private Timestamp lastUpdateDate;
    private List<Tag> tags;
}
