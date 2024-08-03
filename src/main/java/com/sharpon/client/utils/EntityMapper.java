package com.sharpon.client.utils;

import com.sharpon.client.lookup.dto.LookupDto;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.Set;

public interface EntityMapper<D, E> {

    E toEntity(D dto);

    D toDto(E entity);

    List<E> toEntity(List<D> dtoList);

    Set<E> toEntity(Set<D> dtoList);

    List<D> toDto(List<E> entityList);

    Set<D> toDto(Set<E> entityList);

    void update(Set<D> dtoSet, @MappingTarget Set<E> entitySet);

    LookupDto toReferenceDto(E entity);

}
