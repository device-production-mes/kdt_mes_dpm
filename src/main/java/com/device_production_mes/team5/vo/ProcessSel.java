package com.device_production_mes.team5.vo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
@NoArgsConstructor @AllArgsConstructor
public class ProcessSel {
    private int process_id;
    private String product_name;
    private String process_type;
    private String status;
    private int quantity;
    private LocalDateTime start_time;
    private LocalDateTime end_time;
    private String employee_name;

    @Override
    public String toString() {
        switch (process_type) {
            case "ASSEMBLY":
                process_type = "조립";
                break;
            case "SOFTWARE_INSTALL":
                process_type = "소프트웨어 설치";
                break;
            case "INSPECTION":
                process_type = "검사";
                break;
        }
        switch (status) {
            case "IN_PROGRESS":
                status = "진행중";
                break;
            case "CANCELLED":
                status = "취소됨";
                break;
            case "COMPLETED":
                status = "완료됨";
                break;
        }

        return "프로세스ID: " + process_id + "\n" +
                "제품명: " + product_name + "\n" +
                "작업: " + process_type + "\n" +
                "공정 상태: " + status + "\n" +
                "작업 수량: " + quantity + "\n" +
                "공정 시작일: " + start_time.toString().substring(0, 10) + " " + start_time.toString().substring(11) + "\n" +
                "공정 종료일: " + (status.equals("완료됨") ? end_time.toString().substring(0, 10) + " " + end_time.toString().substring(11) : "-") + "\n" +
                "작업자: " + employee_name + "\n" +
                "-".repeat(40);
    }
}
