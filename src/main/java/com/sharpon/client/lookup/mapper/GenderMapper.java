package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.GenderDto;
import com.sharpon.client.lookup.entity.Gender;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface GenderMapper extends EntityMapper<GenderDto, Gender> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Gender partialUpdate(GenderDto dto, @MappingTarget Gender entity);
}