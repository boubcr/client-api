package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.CountryDto;
import com.sharpon.client.lookup.entity.Country;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CountryMapper extends EntityMapper<CountryDto, Country> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Country partialUpdate(CountryDto dto, @MappingTarget Country entity);
}