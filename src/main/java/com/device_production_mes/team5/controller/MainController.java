package com.device_production_mes.team5.controller;

import com.device_production_mes.team5.dao.EmployeeDao;
import com.device_production_mes.team5.dao.ProductDao;
import com.device_production_mes.team5.model.Employee;
import com.device_production_mes.team5.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MainController {
    private static final Scanner SC = new Scanner(System.in);
    private final ProductDao productDao;
    private final EmployeeDao employeeDao;

    @Autowired
    public MainController(ProductDao productDao, EmployeeDao employeeDao) {
        this.productDao = productDao;
        this.employeeDao = employeeDao;
    }

    public void menu() {
        while (true) {
            System.out.println("[1]제품 관리 [2]자재 관리 [3]직원 관리 [4]공정 등록 [5]공정 관리 [6]완제품 관리 [7]종료");
            System.out.print(">> ");
            switch (SC.nextInt()) {
                case 1:
                    productController();
                    break;
                case 2:
                    break;
                case 3:
                    employeeController();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    break;
                case 7:
                    System.out.println("프로그램을 종료 합니다.");
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다.");
                    break;
            }
        }
    }

    private void productController() {
        while (true) {
            System.out.println("[1]제품 등록 [2]제품 조회 [3]돌아가기");
            System.out.print(">> ");
            switch (SC.nextInt()) {
                case 1:
                    boolean result = productDao.insertProduct();
                    System.out.println(result ? "정상 등록 되었습니다." : "등록 실패");
                    break;
                case 2:
                    List<Product> list = productDao.selectProduct();
                    for (Product product : list) {
                        System.out.println(product);
                    }
                    break;
                case 3:
                    return;
                default:
                    System.out.println("잘못 입력하셨습니다.");
                    break;
            }
        }
    }

    private void employeeController() {
        while (true) {
            // 1번 직원등록 2번 직원조회 3번 돌아가기
            System.out.println("[1] 직원 등록 [2] 직원 조회 [3] 돌아가기");
            int select = SC.nextInt();
            switch (select) {
                case 1:
                    boolean result = employeeDao.insertEmployee();
                    System.out.println(result ? "정상 등록 되었습니다." : "등록 실패");
                    break;
                case 2 :
                    List<Employee> list = employeeDao.selectEmployee();
                    for (Employee employee : list) {
                        System.out.println(employee);
                    }
                    break;
                case 3 :
                    return;
                default :
                    System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    private void shipmentController() {
        while (true) {
            // 1번 출하등록 2번 출하내역조회 3번 돌아가기
            // 주의 - 출하등록 시 어떤 product_id에 대한 출하인지 사용자에게 먼저 입력 받아야함
        }
    }
}

