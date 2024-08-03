package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.ProvinceDto;
import com.sharpon.client.lookup.entity.Province;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {CountryMapper.class}
)
public interface ProvinceMapper extends EntityMapper<ProvinceDto, Province> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Province partialUpdate(ProvinceDto dto, @MappingTarget Province entity);
}