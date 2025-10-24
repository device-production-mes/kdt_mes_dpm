package com.device_production_mes.team5.dto;


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

    public Part(int part_id, String part_name) {
        this.part_id = part_id;
        this.part_name = part_name;
    }

    @Override
    public String toString() {
        return part_id + "\t\t" + part_name;
    }
}
