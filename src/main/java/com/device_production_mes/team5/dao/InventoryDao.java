package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class InventoryDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public List<Inventory> searchInventory() {
        List<Inventory> list = null;
        String sql = "select * from Inventory order by Inventory_id";
        try {
            list = jdbcTemplate.query(sql,(rs, rowNum) ->
                    new Inventory(
                            rs.getInt("inventory_id"),
                            rs.getInt("product_id"),
                            rs.getInt("current_stock"),
                            rs.getTimestamp("last_updated").toLocalDateTime()
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}