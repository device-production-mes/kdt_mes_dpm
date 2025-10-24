package com.device_production_mes.team5.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Sp {
    private int shipment_id;
    private String product_name;
    private int quantity;
    private LocalDateTime shipment_date;

    @Override
    public String toString() {
        return "출하id : " + shipment_id + "\n" +
                "제품명 : " + product_name + "\n" +
                "출하 수량 : " + quantity + "\n" +
                "출하 날짜 : " + shipment_date.toString().substring(0, 10) + " " + shipment_date.toString().substring(11);
    }
}
