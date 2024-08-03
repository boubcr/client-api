package com.sharpon.client.address.mapper;

import com.sharpon.client.address.dto.AddressCriteria;
import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.address.entity.Address;
import com.sharpon.client.lookup.mapper.CategoryMapper;
import com.sharpon.client.lookup.mapper.CityMapper;
import com.sharpon.client.lookup.mapper.CountryMapper;
import com.sharpon.client.lookup.mapper.ProvinceMapper;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CityMapper.class, ProvinceMapper.class, CountryMapper.class, CategoryMapper.class})
public interface AddressMapper extends EntityMapper<AddressDto, Address> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Address partialUpdate(AddressDto dto, @MappingTarget Address entity);

    Address toEntity(AddressCriteria criteria);

}