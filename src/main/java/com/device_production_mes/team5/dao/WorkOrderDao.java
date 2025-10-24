package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Status;
import com.device_production_mes.team5.dto.WorkOrder;
import com.device_production_mes.team5.vo.Wp;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
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

    public List<Wp> selectInProgressWorkOrder() {
        List<Wp> list = null;
        String sql = "select work_order_id, product_name, quantity, status from work_order w join product p on w.product_id = p.product_id where status = 'IN_PROGRESS' order by work_order_id desc";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Wp(
                                rs.getInt("work_order_id"),
                                rs.getString("product_name"),
                                rs.getInt("quantity"),
                                rs.getString("status")
                        )
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public void cancelWorkOrder(int work_order_id) {
        String sql = "update work_order set status = ? where work_order_id = ?";
        try {
            jdbcTemplate.update(sql, Status.CANCELLED.toString(), work_order_id);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean searchWorkOrderId(List<Wp> list, int inputId) {
        for (Wp wp : list) {
            if(wp.getWork_order_id() == inputId) {
                return true;
            }
        }
        System.out.println("존재하지 않는 작업ID 입니다.");
        return false;
    }
}
