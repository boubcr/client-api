package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.OccupationDto;
import com.sharpon.client.lookup.entity.Occupation;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface OccupationMapper extends EntityMapper<OccupationDto, Occupation> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Occupation partialUpdate(OccupationDto dto, @MappingTarget Occupation entity);
}