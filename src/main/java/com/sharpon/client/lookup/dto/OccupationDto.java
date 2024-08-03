package com.sharpon.client.lookup.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OccupationDto implements Serializable {
    private Long id;
    private Long version;
    private String code;
    private String name;
}