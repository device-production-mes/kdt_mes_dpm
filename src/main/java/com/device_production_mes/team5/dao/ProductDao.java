package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.model.Product;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class ProductDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public List<Product> selectProduct() {
        List<Product> list = null;
        String sql = "select * from product order by product_id";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new Product(
                            rs.getInt("product_id"),
                            rs.getString("product_name"),
                            rs.getString("model_number")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertProduct() {
        String sql = "insert into product values (product_seq.NEXTVAL, ?, ?)";
        int result = 0;
        try {
            Product product = new Product();
            System.out.print("제품명: ");
            product.setProduct_name(SC.nextLine());
            System.out.print("모델명: ");
            product.setModel_number(SC.next());
            result = jdbcTemplate.update(sql, product.getProduct_name(), product.getModel_number());
            insertBomId();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    public void insertBomId() {
        String sql = "insert into bom values (bom_seq.NEXTVAL, (select max(product_id) from product))";
        try {
            jdbcTemplate.update(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
