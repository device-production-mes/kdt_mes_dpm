package com.device_production_mes.team5.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class WorkOrder {
    private int work_order_id;
    private int product_id;
    private int quantity;
    private LocalDateTime start_date;
    private String status;
}
