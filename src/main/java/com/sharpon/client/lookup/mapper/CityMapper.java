package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.CityDto;
import com.sharpon.client.lookup.entity.City;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CountryMapper.class}
)
public interface CityMapper extends EntityMapper<CityDto, City> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    City partialUpdate(CityDto dto, @MappingTarget City entity);
}