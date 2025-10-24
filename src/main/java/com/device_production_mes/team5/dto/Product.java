package com.device_production_mes.team5.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private int product_id;
    private String product_name;
    private String model_number;

    @Override
    public String toString() {
        return "id: " + product_id + "\n" +
                "제품명: " + product_name + "\n" +
                "모델명: " + model_number;
    }
}
