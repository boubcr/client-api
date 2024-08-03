package com.sharpon.client.address.dto;

import com.sharpon.client.lookup.dto.LookupDto;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class AddressCriteria implements Serializable {
    @NotNull(message = "ClientId is required")
    private Long clientId;
    private String street;
    private String postalCode;
    private LookupDto city;
    private LookupDto province;
    private LookupDto country;
    private LookupDto category;
}
