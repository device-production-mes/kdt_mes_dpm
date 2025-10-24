package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Inventory;
import com.device_production_mes.team5.dto.Shipment;
import com.device_production_mes.team5.vo.Sp;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.http.client.HttpClientProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class ShipmentDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public List<Sp> selectShipment() {
        List<Sp> list = null;
        String sql = "select shipment_id, product_name, quantity, shipment_date from Shipment s join product p on s.product_id = p.product_id order by Shipment_id desc";
        try {
            list = jdbcTemplate.query(sql, (rs,rowNum)  ->
                    new Sp(
                            rs.getInt("shipment_id"),
                            rs.getString("product_name"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("shipment_date").toLocalDateTime()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
    public boolean insertShipment(int product_id) {
        System.out.print("출하 수량을 입력하세요: ");
        int shipmentQuantity = SC.nextInt();
        String sql = "insert into shipment values (shipment_seq.NEXTVAL, ?, ?, sysdate)";
        int result = 0;
        int currentInventory = 0;
        try {
            String Sql = "SELECT current_stock FROM inventory WHERE product_id = ?";
            Integer count = jdbcTemplate.queryForObject(Sql, Integer.class, product_id);
            if (count != null) {
                currentInventory = count;
            }
            if (shipmentQuantity > currentInventory) {
                throw new RuntimeException("재고 부족");
            }
            result = jdbcTemplate.update(sql, product_id, shipmentQuantity);
            if (result > 0) {
                String inventoryUpdateSql = "update inventory set current_stock = current_stock - ? where product_id = ?";
                jdbcTemplate.update(inventoryUpdateSql, shipmentQuantity, product_id);
                System.out.println("출하 및 재고 업데이트 완료");
            }
            return true;
        } catch (RuntimeException e) {
            if (e.getMessage().equals("재고 부족")) {
                System.out.printf("등록된 재고가 부족합니다. (재고: %d개, 요청: %d개)%n", currentInventory, shipmentQuantity);
                return false;
            }
            System.err.println("출하 처리 중 예상치 못한 Runtime 오류가 발생했습니다.");
            e.printStackTrace();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
