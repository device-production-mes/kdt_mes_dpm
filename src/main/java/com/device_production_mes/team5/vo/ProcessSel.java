package com.device_production_mes.team5.vo;

import lombok.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@RequiredArgsConstructor
public class ProcessSel {
    private final int process_id;
    private final String product_name;
    private final String process_type;
    private final String status;
    private final int quantity;
    private final LocalDateTime start_time;
    private final LocalDateTime end_time;
    private final String employee_name;

    @Override
    public String toString() {
        String processTypeKor;
        switch (process_type) {
            case "ASSEMBLY":
                processTypeKor = "조립";
                break;
            case "SOFTWARE_INSTALL":
                processTypeKor = "소프트웨어 설치";
                break;
            case "INSPECTION":
                processTypeKor = "검사";
                break;
            default:
                processTypeKor = process_type;
                break;
        }

        String statusKor;
        switch (status) {
            case "IN_PROGRESS":
                statusKor = "진행중";
                break;
            case "CANCELLED":
                statusKor = "취소됨";
                break;
            case "COMPLETED":
                statusKor = "완료됨";
                break;
            default:
                statusKor = status;
                break;
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        String startTimeStr = start_time != null ? start_time.format(formatter) : "-";
        String endTimeStr = (statusKor.equals("완료됨") && end_time != null)
                ? end_time.format(formatter)
                : "-";

        return "프로세스ID: " + process_id + "\n" +
                "제품명: " + product_name + "\n" +
                "작업: " + processTypeKor + "\n" +
                "공정 상태: " + statusKor + "\n" +
                "작업 수량: " + quantity + "\n" +
                "공정 시작일: " + startTimeStr + "\n" +
                "공정 종료일: " + endTimeStr + "\n" +
                "작업자: " + employee_name + "\n" +
                "-".repeat(40);
    }
}
