package com.sharpon.client.lookup.mapper;

import com.sharpon.client.lookup.dto.CategoryDto;
import com.sharpon.client.lookup.entity.Category;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE, componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper extends EntityMapper<CategoryDto, Category> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Category partialUpdate(CategoryDto dto, @MappingTarget Category entity);
}