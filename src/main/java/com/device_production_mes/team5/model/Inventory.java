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

    @Override
    public String toString() {
        return "재고id : " + inventory_id + "\n" +
                "제품id : " + product_id + "\n" +
                "재고수량 : " + current_stock + "\n" +
                "최근등록일 : " + last_update;

    }
}