package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Employee;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class EmployeeDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public List<Employee> selectEmployee() {
        List<Employee> list = null;
        String sql = "select * from employee order by employee_id";
        try {
            list = jdbcTemplate.query(sql,(rs, rowNum) ->
                    new Employee(
                            rs.getInt("employee_id"),
                            rs.getString("employee_name")
                    )
            );
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insertEmployee() {
        String sql = "insert into employee values (employee_seq.NEXTVAL, ?)";
        int result = 0;
        try {
            Employee employee = new Employee();
            System.out.print("사원명 : ");
            employee.setEmployee_name(SC.nextLine());
            result = jdbcTemplate.update(sql, employee.getEmployee_name());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    public boolean selectByIdEmployee(List<Employee> list2, int inputId) {
        for (Employee employee : list2) {
            if(employee.getEmployee_id() == inputId) return true;
        }
        return false;
    }

    public boolean deleteEmployee(int employee_id) {
        int result = 0;
        System.out.println("정말 해고 하시겠습니까...? [y/n]");
        System.out.print(">> ");
        if (SC.next().equalsIgnoreCase("y")) {
            String sql = "delete employee where employee_id = ?";
            try {
                result = jdbcTemplate.update(sql, employee_id);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return result > 0;
    }
}