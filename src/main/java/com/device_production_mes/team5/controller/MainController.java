package com.device_production_mes.team5.controller;

import com.device_production_mes.team5.dao.*;
import com.device_production_mes.team5.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MainController {
    private static final Scanner SC = new Scanner(System.in);
    private final ProductDao productDao;
    private final BomDao bomDao;
    private final EmployeeDao employeeDao;
    private final InventoryDao inventoryDao;
    private final ShipmentDao shipmentDao;

    @Autowired
    public MainController(ProductDao productDao, BomDao bomDao, EmployeeDao employeeDao, InventoryDao inventoryDao, ShipmentDao shipmentDao) {
        this.productDao = productDao;
        this.bomDao = bomDao;
        this.employeeDao = employeeDao;
        this.inventoryDao = inventoryDao;
        this.shipmentDao = shipmentDao;
    }

    public void menu() {
        while (true) {
            System.out.println("[1]제품 관리 [2]자재 관리 [3]직원 관리 [4]공정 등록 [5]공정 관리 [6]완제품 조회 [7]출하 관리 [8]종료");
            System.out.print(">> ");
            switch (SC.nextInt()) {
                case 1:
                    productController();
                    break;
                case 2:
                    bomController();
                    break;
                case 3:
                    employeeController();
                    break;
                case 4:
                    break;
                case 5:
                    break;
                case 6:
                    inventoryController();
                    break;
                case 7:
                    shipmentController();
                    break;
                case 8:
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

    private void bomController() {
        while (true) {
            System.out.println("[1]자재 입고 [2]자재 조회 [3]돌아가기");
            System.out.print(">> ");
            switch (SC.nextInt()) {
                case 1:
                    List<Part> list = bomDao.selectPart();
                    System.out.println("자재id\t자재명");
                    for (Part part : list) {
                        System.out.println(part);
                    }
                    System.out.println("추가할 자재의 id를 입력해 주세요: ");
                    System.out.print(">> ");
                    bomDao.addQuantity(SC.nextInt());
                    break;
                case 2:
                    List<Quantity> list2 = bomDao.selectBomDetail();
                    System.out.printf("%-12s | %5s\n", "자재명", "수량");
                    for (Quantity quantity : list2) {
                        System.out.printf("%-12s | %6d\n", quantity.getPart_name(), quantity.getQuantity());
                    }
                    break;
                case 3:
                    return;
                default:
                    break;
            }
            break;
        }
    }

    private void employeeController() {
        while (true) {
            // 1번 직원등록 2번 직원조회 3번 돌아가기
            System.out.println("[1] 직원 등록 [2] 직원 조회 [3] 돌아가기");
            System.out.print(">> ");
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
            System.out.println("[1]출하등록 [2]출하내역조회 [3]돌아가기");
            System.out.print(">> ");
            switch (SC.nextInt()) {
                case 1:
                    List<Product> list = productDao.selectProduct();
                    System.out.println("제품id\t제품명");
                    for (Product product : list) {
                        System.out.println(product.getProduct_id() + "\t\t" + product.getProduct_name());
                    }
                    System.out.println("출하 하실 제품의 id를 입력해 주세요.");
                    System.out.print(">> ");
                    int product_id = SC.nextInt();
                    boolean result = shipmentDao.insertShipment(product_id);
                    System.out.println(result ? "정상등록 되었습니다." : "등록에 실패 하였습니다.");
                    break;

                case 2:
                    List<Shipment> list2 = shipmentDao.selectShipment();
                    for (Shipment shipment : list2) {
                        System.out.println(shipment);
                    }
                    break;

                case 3: return;

                default: System.out.println("잘못 입력하셨습니다.");
            }
        }
    }

    private void inventoryController() {
        System.out.println("===== 제품 재고 관리 =====");
        List<Inventory> list3 = inventoryDao.searchInventory();
        System.out.println("제품id\t재고 수량");
        for (Inventory inventory : list3) {
            System.out.println(inventory.getProduct_id() + "\t\t" + inventory.getCurrent_stock());
        }
    }
}

