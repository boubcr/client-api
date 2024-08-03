package com.sharpon.client.client.controller;

import com.sharpon.client.client.controller.ClientController;
import com.sharpon.client.client.dto.ClientCriteria;
import com.sharpon.client.client.dto.ClientDto;
import com.sharpon.client.client.service.ClientService;
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
class ClientControllerTest {

    @Mock
    ClientService clientService;

    @InjectMocks
    ClientController clientController;

    @Test
    void delete_withValidInputs_shouldDeleteSuccessfully() {
        doNothing().when(clientService).deleteById(anyLong());
        ResponseEntity<Void> response = clientController.delete(anyLong());
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    void search_withValidPageable_findPagedList() {
        Page<ClientDto> expected = new PageImpl<>(
                Arrays.asList(
                        ClientDto.builder().id(1L).code("100000000001").build(),
                        ClientDto.builder().id(2L).code("100000000002").build(),
                        ClientDto.builder().id(3L).code("100000000003").build()
                )
        );

        when(clientService.search(any(ClientCriteria.class), any(Pageable.class))).thenReturn(expected);

        ClientCriteria criteria = ClientCriteria.builder().build();
        ResponseEntity<Page<ClientDto>> response = clientController.search(criteria, Pageable.unpaged());
        Page<ClientDto> actual = response.getBody();

        assertEquals(expected, actual);
        verify(clientService, times(1)).search(criteria, Pageable.unpaged());
    }

    @Test
    void update_withValidInput_updatesSuccessfully() {
        ClientDto dto = ClientDto.builder().id(1L).code("100000000001").build();
        when(clientService.update(any(ClientDto.class))).thenReturn(dto);
        ClientDto response = clientController.update(dto);
        assertEquals(dto, response);
    }

    @Test
    void creates_withValidInput_createsSuccessfully() {
        ClientDto dto = ClientDto.builder().id(1L).code("100000000001").build();
        when(clientService.create(any(ClientDto.class))).thenReturn(dto);
        ClientDto response = clientController.create(dto);
        assertEquals(dto, response);
    }

    @Test
    void findId_withValidId_shouldFindsSuccessfully() {
        ClientDto dto = ClientDto.builder().id(1L).code("100000000001").build();
        when(clientService.findById(anyLong())).thenReturn(dto);
        ClientDto response = clientController.findById(1L);
        assertEquals(dto, response);
    }

}
