package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Shipment;
import com.device_production_mes.team5.vo.Sp;
import lombok.RequiredArgsConstructor;
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