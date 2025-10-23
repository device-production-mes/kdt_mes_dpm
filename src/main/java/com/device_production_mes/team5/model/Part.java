package com.device_production_mes.team5.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor @NoArgsConstructor
public class Part {
    private int part_id;
    private String part_name;
    private String part_number;

    @Override
    public String toString() {
        return part_id + "\t\t" + part_name;
    }
}
