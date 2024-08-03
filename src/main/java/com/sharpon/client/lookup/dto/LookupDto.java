package com.sharpon.client.lookup.dto;

import lombok.*;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LookupDto implements Serializable {
    private Long id;
    private Long version;
    private String code;
    private String name;
}