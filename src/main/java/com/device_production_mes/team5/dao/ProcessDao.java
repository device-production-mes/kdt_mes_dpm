package com.device_production_mes.team5.dao;

import com.device_production_mes.team5.dto.Employee;
import com.device_production_mes.team5.dto.Process;
import com.device_production_mes.team5.dto.ProcessType;
import com.device_production_mes.team5.vo.ProcessSel;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Scanner;

@Repository
@RequiredArgsConstructor
public class ProcessDao {
    private final static Scanner SC = new Scanner(System.in);
    private final JdbcTemplate jdbcTemplate;

    public boolean insertProcess(int work_order_id) {
        int result = 0;
        Process process = new Process();
        process.setWork_order_id(work_order_id);
        String sql = "";
        if(setProcessType(process)) {
            sql = "select * from employee";
            List<Employee> list = jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Employee(
                                rs.getInt("employee_id"),
                                rs.getString("employee_name")
                        )
                    );
            System.out.println("직원ID \t 직원명");
            for (Employee employee : list) {
                System.out.println(employee.getEmployee_id() + " \t " + employee.getEmployee_name());
            }
            if(!setEmployee(list, process)) {
                return false;
            }
        } else {
            return false;
        }
        sql = "insert into process values (process_seq.NEXTVAL, ?, ?, sysdate, null, ?)";
        try {
            result = jdbcTemplate.update(sql, process.getWork_order_id(), process.getProcess_type().toString(), process.getEmployee_id());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result > 0;
    }

    public List<ProcessSel> selectProcess() {
        List<ProcessSel> list = null;
        String sql = "select process_id, product_name, process_type, status, quantity, start_time, end_time, employee_name from process p join work_order w on p.work_order_id = w.work_order_id join product pr on w.product_id = pr.product_id join employee e on p.employee_id = e.employee_id";
        try {
            list = jdbcTemplate.query(sql, (rs, rowNum) ->
                    new ProcessSel(
                            rs.getInt("process_id"),
                            rs.getString("product_name"),
                            rs.getString("process_type"),
                            rs.getString("status"),
                            rs.getInt("quantity"),
                            rs.getTimestamp("start_time").toLocalDateTime(),
                            rs.getTimestamp("end_time").toLocalDateTime(),
                            rs.getString("employee_name")
                    )
            );
        } catch (Exception e) {
           e.printStackTrace();
        }
        return list;
    }

    private boolean setProcessType(Process process) {
        while (true) {
            System.out.println("프로세스 타입을 선택해 주세요.");
            System.out.println("[1]조립 [2]소프트웨어 설치 [3]검사 [4]돌아가기");
            System.out.print(">> ");
            if(SC.hasNextInt()) {
                switch (SC.nextInt()) {
                    case 1:
                        process.setProcess_type(ProcessType.ASSEMBLY);
                        break;
                    case 2:
                        process.setProcess_type(ProcessType.SOFTWARE_INSTALL);
                        break;
                    case 3:
                        process.setProcess_type(ProcessType.INSPECTION);
                        break;
                    case 4:
                        return false;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                        continue;
                }
                return true;
            } else {
                System.out.println("잘못 입력하셨습니다.");
                SC.next();
            }
        }
    }

    private boolean setEmployee(List<Employee> list, Process process) {
        System.out.println("작업자의 직원ID를 입력해 주세요.");
        System.out.print(">> ");
        int inputId = SC.nextInt();
        for (Employee employee : list) {
            if(employee.getEmployee_id() == inputId) {
                process.setEmployee_id(inputId);
                return true;
            }
        }
        System.out.println("잘못 입력하셨습니다.");
        return false;
    }
}
