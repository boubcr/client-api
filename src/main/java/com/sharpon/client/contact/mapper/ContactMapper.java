package com.sharpon.client.contact.mapper;

import com.sharpon.client.contact.dto.ContactCriteria;
import com.sharpon.client.contact.dto.ContactDto;
import com.sharpon.client.contact.entity.Contact;
import com.sharpon.client.lookup.mapper.CategoryMapper;
import com.sharpon.client.lookup.mapper.ContactTypeMapper;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {ContactTypeMapper.class, CategoryMapper.class})
public interface ContactMapper extends EntityMapper<ContactDto, Contact> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Contact partialUpdate(ContactDto dto, @MappingTarget Contact entity);

    Contact toEntity(ContactCriteria criteria);
}