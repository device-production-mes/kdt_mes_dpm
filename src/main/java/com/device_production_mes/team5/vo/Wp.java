package com.device_production_mes.team5.vo;

import lombok.*;

@Getter
@RequiredArgsConstructor
public class Wp {
    private final int work_order_id;
    private final String product_name;
    private final int quantity;
    private final String status;
}
