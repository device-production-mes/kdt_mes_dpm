package com.device_production_mes.team5.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class Employee {
    private int employee_id;
    private String employee_name;

    @Override
    public String toString() {
        return "사원명 : " + employee_name + "\n" +
                "사원 아이디 : " + employee_id;
    }

}
