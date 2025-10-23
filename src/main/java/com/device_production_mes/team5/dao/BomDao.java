package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.model.BomDetail;
import com.device_production_mes.team5.model.Part;
import com.device_production_mes.team5.model.Quantity;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class BomDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public List<Part> selectPart() {
        List<Part> list = null;
        String sql = "select * from part";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Part(
                        rs.getInt("part_id"),
                            rs.getString("part_name"),
                            rs.getString("part_number")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean addQuantity(int part_id) {
        int result = 0;
        String sql = "select count(*) from bom_detail where part_id = ?";
        try {
            result = jdbcTemplate.queryForObject(sql, Integer.class, part_id);
            if (result == 0) {
                return false;
            }
            System.out.print("추가할 수량: ");
            int plus = SC.nextInt();
            sql = "update bom_detail set quantity = quantity + ? where part_id = ?";
            jdbcTemplate.update(sql, plus, part_id);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    public List<Quantity> selectBomDetail() {
        List<Quantity> list = null;
        String sql = "select DISTINCT part_name, quantity from part p join bom_detail b on p.PART_ID = b.PART_ID order by part_name";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Quantity(
                            rs.getString("part_name"),
                            rs.getInt("quantity")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
