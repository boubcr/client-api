package com.sharpon.client.client.dto;

import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.contact.dto.ContactDto;
import com.sharpon.client.lookup.dto.CompanyDto;
import com.sharpon.client.lookup.dto.LookupDto;
import com.sharpon.client.lookup.dto.NationalityDto;
import com.sharpon.client.lookup.dto.OccupationDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Set;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClientDto implements Serializable {
    private Long id;
    private Long version;
    private String code;
    @NotNull(message = "Firstname is required")
    private String firstName;
    @NotNull(message = "Lastname is required")
    private String lastName;
    private LocalDate dateOfBirth;
    private BigDecimal salary;
    @NotNull(message = "Gender is required")
    private LookupDto gender;
    private NationalityDto nationality;
    private OccupationDto occupation;
    private CompanyDto company;
    private Set<AddressDto> addresses;
    private Set<ContactDto> contacts;
}