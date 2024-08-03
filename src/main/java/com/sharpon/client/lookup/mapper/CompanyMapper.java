package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.CompanyDto;
import com.sharpon.client.lookup.entity.Company;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CompanyMapper extends EntityMapper<CompanyDto, Company> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Company partialUpdate(CompanyDto dto, @MappingTarget Company entity);
}