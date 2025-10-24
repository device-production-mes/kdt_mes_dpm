package com.device_production_mes.team5.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class Process {
    private int process_id;
    private int work_order_id;
    private ProcessType process_type;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private int employee_id;
}
