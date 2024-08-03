package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.NationalityDto;
import com.sharpon.client.lookup.entity.Nationality;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface NationalityMapper extends EntityMapper<NationalityDto, Nationality> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Nationality partialUpdate(NationalityDto dto, @MappingTarget Nationality entity);
}