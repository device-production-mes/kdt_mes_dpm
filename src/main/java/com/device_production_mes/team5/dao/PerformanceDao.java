package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Performance;
import com.device_production_mes.team5.dto.Status;
import com.device_production_mes.team5.vo.Pp;
import com.device_production_mes.team5.vo.ProcessSel;
import com.device_production_mes.team5.vo.Sb;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class PerformanceDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public boolean insertPerformance(int process_id, int quantity) {
        int result = 0;
        Performance performance = new Performance();
        performance.setProcess_id(process_id);
        System.out.print("양품 수량: ");
        int goodQuantity = SC.nextInt();
        if(goodQuantity > quantity) {
            System.out.println("잘못 입력하셨습니다.");
            return false;
        }
        performance.setGood_quantity(goodQuantity);
        performance.setDefect_quantity(quantity - goodQuantity);
        performance.setInspection_status("PASSED");
        String sql = "insert into performance values (performance_seq.NEXTVAL, ?, ?, ?, ?, sysdate)";
        try {
            result = jdbcTemplate.update(sql, performance.getProcess_id(), performance.getGood_quantity(), performance.getDefect_quantity(), performance.getInspection_status());
            if(result > 0) {
                sql = "update work_order set status = ? where work_order_id = (select work_order_id from process where process_id = ?)";
                jdbcTemplate.update(sql, Status.COMPLETED.toString(), performance.getProcess_id());
                sql = "select count(product_id), product_id from inventory where product_id = (select product_id from process p join work_order w on p.work_order_id = w.work_order_id where process_id = ?) group by product_id";
                List<Sb> count = jdbcTemplate.query(sql, (rs, rowNum) ->
                            new Sb(
                                    rs.getInt(1),
                                    rs.getInt(2)
                            ),
                        performance.getProcess_id()
                        );

                if(!count.isEmpty()) {
                    sql = "update inventory set current_stock = current_stock + ? where product_id = ?";
                    jdbcTemplate.update(sql, performance.getGood_quantity(), count.get(0).getProduct_id());
                } else {
                    sql = "select product_id from process p join work_order w on p.work_order_id = w.work_order_id where process_id = ?";
                    Integer product_id = jdbcTemplate.queryForObject(sql, Integer.class, performance.getProcess_id());
                    sql = "insert into inventory values (inventory_seq.NEXTVAL, ?, ?, sysdate)";
                    jdbcTemplate.update(sql, product_id, performance.getGood_quantity());
                }
                sql = "update process set end_time = sysdate where process_id = ?";
                result = jdbcTemplate.update(sql, performance.getProcess_id());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    public static int searchProcessId(List<ProcessSel> list, int inputId) {
        for (ProcessSel processSel : list) {
            if(processSel.getProcess_id() == inputId) {
                return processSel.getQuantity();
            }
        }
        return 0;
    }

    public List<Pp> selectPerformance() {
        List<Pp> list = null;
        String sql = "select product_name, good_quantity, defect_quantity, inspection_date from performance pe join process p on pe.process_id = p.process_id join work_order w on p.work_order_id = w.work_order_id join product pr on pr.product_id = w.product_id";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Pp(
                                rs.getString(1),
                                rs.getInt(2),
                                rs.getInt(3),
                                rs.getTimestamp(4).toLocalDateTime()
                        )
                    );
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }
}
