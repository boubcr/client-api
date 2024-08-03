package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.ContactTypeDto;
import com.sharpon.client.lookup.entity.ContactType;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface ContactTypeMapper extends EntityMapper<ContactTypeDto, ContactType> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    ContactType partialUpdate(ContactTypeDto dto, @MappingTarget ContactType entity);
}