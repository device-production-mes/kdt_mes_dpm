package com.device_production_mes.team5.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Pp {
    private String product_name;
    private int good_quantity;
    private int defect_quantity;
    private LocalDateTime inspection_date;

}
