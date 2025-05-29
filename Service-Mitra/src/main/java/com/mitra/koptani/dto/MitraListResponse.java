package com.mitra.koptani.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MitraListResponse {

    private Integer id;

    private String slug;

    private String name;

    private String phoneNumber;

    // tipe mitra, misal 'supplier', 'distributor', 'retailer'
    private String type;

    private String status;
}
