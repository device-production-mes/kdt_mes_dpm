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

    @Override
    public String toString() {
        return "출하id : " + shipment_id + "\n" +
                "제품id : " + product_id + "\n" +
                "출하 수량 : " + quantity + "\n" +
                "출하 날짜 : " + shipment_date.toString().substring(0, 10) + " " + shipment_date.toString().substring(11);
    }
}