package com.device_production_mes.team5.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Wp {
    private int work_order_id;
    private String product_name;
    private int quantity;
    private String status;
}
