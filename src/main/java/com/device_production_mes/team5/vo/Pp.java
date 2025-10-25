package com.device_production_mes.team5.vo;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class Pp {
    private final String product_name;
    private final int good_quantity;
    private final int defect_quantity;
    private final LocalDateTime inspection_date;

}
