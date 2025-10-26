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
    private final int work_order_id;

    @Override
    public String toString() {
        String[] types = process_type.split(",\\s*");
        for (int i = 0; i < types.length; i++) {
            switch (types[i]) {
                case "ASSEMBLY":
                    types[i] = "조립";
                    break;
                case "SOFTWARE_INSTALL":
                    types[i] = "소프트웨어 설치";
                    break;
                case "INSPECTION":
                    types[i] = "검사";
                    break;
                default:
                    break;
            }
        }
        String processType = String.join(", ", types);

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
                "작업: " + processType + "\n" +
                "공정 상태: " + statusKor + "\n" +
                "작업 수량: " + quantity + "\n" +
                "공정 시작일: " + startTimeStr + "\n" +
                "공정 종료일: " + endTimeStr + "\n" +
                "작업자: " + employee_name + "\n" +
                "-".repeat(40);
    }
}
