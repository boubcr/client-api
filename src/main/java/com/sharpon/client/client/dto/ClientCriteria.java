package com.sharpon.client.client.dto;

import com.sharpon.client.lookup.dto.LookupDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
public class ClientCriteria implements Serializable {
    private String code;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;
    private BigDecimal salary;
    private LookupDto gender;
    private LookupDto nationality;
    private LookupDto occupation;
    private LookupDto company;
}
