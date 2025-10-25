package com.device_production_mes.team5.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@AllArgsConstructor
public class Sp {
    private final int shipment_id;
    private final String product_name;
    private final int quantity;
    private final LocalDateTime shipment_date;

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String shipmentDateStr = (shipment_date != null)
                ? shipment_date.format(formatter)
                : "-";

        return "출하 ID: " + shipment_id + "\n" +
                "제품명: " + product_name + "\n" +
                "출하 수량: " + quantity + "\n" +
                "출하 날짜: " + shipmentDateStr + "\n" +
                "-".repeat(40);
    }
}