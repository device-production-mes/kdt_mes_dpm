package com.device_production_mes.team5.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Bom {
    private BomDetail bomDetail;
    private Product product;
}
