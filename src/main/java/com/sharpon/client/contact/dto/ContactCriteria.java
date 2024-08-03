package com.sharpon.client.contact.dto;

import com.sharpon.client.lookup.dto.LookupDto;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class ContactCriteria implements Serializable {
    private String value;
    private LookupDto contactType;
    private LookupDto category;
    private boolean enabled;
}
