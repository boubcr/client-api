package com.sharpon.client.address.controller;

import com.sharpon.client.address.dto.AddressCriteria;
import com.sharpon.client.address.dto.AddressDto;
import com.sharpon.client.address.service.AddressService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressControllerTest {

    @Mock
    AddressService addressService;

    @InjectMocks
    AddressController addressController;

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(addressService).deleteById(anyLong());
        ResponseEntity<Void> response = addressController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<AddressDto> expected = new PageImpl<>(
                Arrays.asList(
                        AddressDto.builder().id(1L).postalCode("001").street("Item 1").build(),
                        AddressDto.builder().id(2L).postalCode("002").street("Item 2").build(),
                        AddressDto.builder().id(3L).postalCode("003").street("Item 3").build()
                )
        );

        when(addressService.search(any(AddressCriteria.class), any(Pageable.class))).thenReturn(expected);

        AddressCriteria criteria = AddressCriteria.builder().build();
        ResponseEntity<Page<AddressDto>> response = addressController.search(criteria, Pageable.unpaged());
        Page<AddressDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(addressService, times(1)).search(criteria, Pageable.unpaged());
    }

    @Test
    void update_withValidInput_updatesSuccessfully() {
        AddressDto dto = AddressDto.builder().id(1L).postalCode("001").street("Item 1").build();
        when(addressService.update(any(AddressDto.class))).thenReturn(dto);
        AddressDto response = addressController.update(dto);
        assertEquals(dto, response);
    }

    @Test
    void creates_withValidInput_createsSuccessfully() {
        AddressDto dto = AddressDto.builder().id(1L).postalCode("001").street("Item 1").build();
        when(addressService.create(any(AddressDto.class), anyLong())).thenReturn(dto);
        AddressDto response = addressController.create(dto, 1L);
        assertEquals(dto, response);
    }

}
