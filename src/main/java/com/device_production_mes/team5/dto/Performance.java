package com.device_production_mes.team5.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter @Getter
@AllArgsConstructor @NoArgsConstructor
public class Performance {
    private int performance;
    private int process_id;
    private int good_quantity;
    private int defect_quantity;
    private String inspection_status;
    private LocalDateTime inspection_date;
}
