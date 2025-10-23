package com.device_production_mes.team5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class BomDetail {
    private int bom_detail_id;
    private int bom_id;
    private int part_id;
    private int quantity;
}
