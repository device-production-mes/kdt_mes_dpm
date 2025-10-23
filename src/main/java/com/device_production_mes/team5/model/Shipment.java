package com.device_production_mes.team5.model;


import lombok.*;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Shipment {
    private int shipment_id;
    private int product_id;
    private int quantity;
    private LocalDateTime shipment_date;
}
