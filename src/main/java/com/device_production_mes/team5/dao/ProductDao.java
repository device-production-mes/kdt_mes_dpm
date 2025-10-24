package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Part;
import com.device_production_mes.team5.dto.Product;
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

    public List<Product> selectProducts() {
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

    public List<Product> selectProduct() {
        List<Product> list = null;
        String sql = "select p.* from product p join inventory inv on p.product_id = inv.product_id order by p.product_id";
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
            sql = "select * from part";
            List<Part> list = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Part(
                                rs.getInt(1),
                                rs.getString(2)
                        )
                    );
            while (true) {
                System.out.println("자재ID\t자재명");
                for (Part part : list) {
                    System.out.println(part.getPart_id() + "\t" + part.getPart_name());
                }
                System.out.println("종료 하시려면 '0'을 입력해 주세요.");
                System.out.println("제품의 필요한 자재ID를 입력해 주세요.");
                System.out.print(">> ");
                int inputId = SC.nextInt();
                if(inputId == 0) {
                    return true;
                }
                else if(inputId > 200 && inputId < 208) {
                    insertBomDetail(inputId);
                } else {
                    System.out.println("잘못 입력하셨습니다.");
                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return result > 0;
    }

    private void insertBomDetail(int inputId) {
        String sql = "select count(*) from bom_detail where part_id = ? and bom_id = (select max(bom_id) from bom)";
        try {
            Integer count = jdbcTemplate.queryForObject(sql, Integer.class, inputId);
            if(count > 0) {
                System.out.println("[!]이미 등록된 자재입니다.");
                return;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        sql = "insert into bom_detail values (bom_detail_seq.NEXTVAL, (select max(bom_id) from bom), ?, (select max(quantity) from bom_detail where part_id = ?))";
        try {
            jdbcTemplate.update(sql, inputId, inputId);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
