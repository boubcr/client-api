package com.sharpon.client.address.dto;

import com.sharpon.client.lookup.dto.CategoryDto;
import com.sharpon.client.lookup.dto.CityDto;
import com.sharpon.client.lookup.dto.CountryDto;
import com.sharpon.client.lookup.dto.ProvinceDto;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {
    private Long id;
    private Long version;
    private String street;
    private String postalCode;
    @NotNull(message = "City is required")
    private CityDto city;
    private ProvinceDto province;
    @NotNull(message = "Country is required")
    private CountryDto country;
    private CategoryDto category;
    private boolean enabled;
}