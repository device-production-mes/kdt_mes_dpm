package com.device_production_mes.team5.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class Inventory {
    private int inventory_id;
    private int product_id;
    private int current_stock;
    private LocalDateTime last_update;
}
