package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.model.Product;
import com.device_production_mes.team5.model.Shipment;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.management.openmbean.TabularType;
import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class ShipmentDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public List<Shipment> selectShipment() {
        List<Shipment> list = null;
        String sql = "select * from Shipment order by Shipment_id";
        try {
            list = jdbcTemplate.query(sql, (rs,rowNum)  ->
                    new Shipment(
                            rs.getInt("shipment_id"),
                            rs.getInt("product_id"),
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
        String sql = "insert into shipment values (shipment_seq.NEXTVAL, ?, ?, sysdate)";
        int result = 0;
        try {
            Shipment shipment = new Shipment();
            System.out.print("출하량 : ");
            shipment.setQuantity(SC.nextInt());
            result = jdbcTemplate.update(sql, product_id,
                    shipment.getQuantity());
        } catch (Exception e) {
            e.printStackTrace();
        } return result > 0;
    }
}