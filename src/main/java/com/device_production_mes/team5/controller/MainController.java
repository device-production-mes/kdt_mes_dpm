package com.device_production_mes.team5.controller;

import com.device_production_mes.team5.dao.InventoryDao;
import com.device_production_mes.team5.dao.ProductDao;
import com.device_production_mes.team5.dao.ShipmentDao;
import com.device_production_mes.team5.model.Inventory;
import com.device_production_mes.team5.model.Product;
import com.device_production_mes.team5.model.Shipment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class MainController {
    private static final Scanner SC = new Scanner(System.in);
    private final ProductDao productDao;
    private final ShipmentDao shipmentDao;
    private final InventoryDao inventoryDao;

    @Autowired
    public MainController(ProductDao productDao, ShipmentDao shipmentDao, InventoryDao inventoryDao) {
        this.productDao = productDao;
        this.shipmentDao = shipmentDao;
        this.inventoryDao = inventoryDao;
    }

    public void menu() {
        while (true) {
            System.out.println("[1]제품 관리 [2]자재 관리 [3]직원 관리 [4]공정 등록 [5]공정 관리 [6]완제품 관리 [7]출하 관리 [8]종료");
            System.out.print(">> ");
            switch (SC.nextInt()) {
                case 1:
                    productController();
                    break;
                case 2:
                    break;
                case 3:
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

    private void employeeController() {
        while (true) {
            // 1번 직원등록 2번 직원조회 3번 돌아가기
        }
    }

    private void shipmentController() {
        while (true) {
            // 1번 출하등록 2번 출하내역조회 3번 돌아가기
            // 주의 - 출하등록 시 어떤 product_id에 대한 출하인지 사용자에게 먼저 입력 받아야함
            System.out.println("[1]출하등록 [2]출하내역조회 [3]돌아가기");
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

