package com.sharpon.client.client.mapper;

import com.sharpon.client.address.mapper.AddressMapper;
import com.sharpon.client.client.dto.ClientCriteria;
import com.sharpon.client.client.dto.ClientDto;
import com.sharpon.client.client.entity.Client;
import com.sharpon.client.contact.mapper.ContactMapper;
import com.sharpon.client.lookup.mapper.CompanyMapper;
import com.sharpon.client.lookup.mapper.NationalityMapper;
import com.sharpon.client.lookup.mapper.OccupationMapper;
import com.sharpon.client.utils.EntityMapper;
import org.mapstruct.*;

@Mapper(unmappedTargetPolicy = ReportingPolicy.IGNORE,
        componentModel = MappingConstants.ComponentModel.SPRING,
        uses = {AddressMapper.class, ContactMapper.class, NationalityMapper.class, OccupationMapper.class, CompanyMapper.class}
)
public interface ClientMapper extends EntityMapper<ClientDto, Client> {
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    Client partialUpdate(ClientDto dto, @MappingTarget Client entity);

    Client toEntity(ClientCriteria criteria);
}