package com.sharpon.client.contact.dto;

import com.sharpon.client.lookup.dto.CategoryDto;
import com.sharpon.client.lookup.dto.ContactTypeDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContactDto implements Serializable {
    private Long id;
    private Long version;
    private String value;
    private ContactTypeDto contactType;
    private CategoryDto category;
    private boolean enabled;
}