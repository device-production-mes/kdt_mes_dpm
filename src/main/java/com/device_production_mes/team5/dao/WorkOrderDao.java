package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.WorkOrder;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class WorkOrderDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public boolean insertWorkOrder(WorkOrder workOrder) {
        int result = 0;
        String sql = "insert into work_order values (work_order_seq.NEXTVAL, ?, ?, sysdate, ?)";
        try {
            result = jdbcTemplate.update(sql, workOrder.getProduct_id(), workOrder.getQuantity(), workOrder.getStatus().toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
        return result > 0;
    }
}
