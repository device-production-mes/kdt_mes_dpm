package com.device_production_mes.team5.controller;

import com.device_production_mes.team5.dao.*;
import com.device_production_mes.team5.dto.*;
import com.device_production_mes.team5.vo.ProcessSel;
import com.device_production_mes.team5.vo.Quantity;
import com.device_production_mes.team5.vo.Sp;
import com.device_production_mes.team5.vo.Wp;
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
    private final WorkOrderDao workOrderDao;
    private final ProcessDao processDao;

    @Autowired
    public MainController(ProductDao productDao, BomDao bomDao, EmployeeDao employeeDao, InventoryDao inventoryDao, ShipmentDao shipmentDao, WorkOrderDao workOrderDao, ProcessDao processDao) {
        this.productDao = productDao;
        this.bomDao = bomDao;
        this.employeeDao = employeeDao;
        this.inventoryDao = inventoryDao;
        this.shipmentDao = shipmentDao;
        this.workOrderDao = workOrderDao;
        this.processDao = processDao;
    }

    public void menu() {
        while (true) {
            System.out.println("[1]제품 관리 [2]자재 관리 [3]직원 관리 [4]작업 등록 [5]공정 관리 [6]완제품 조회 [7]출하 관리 [8]종료");
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
                    workOrderController();
                    break;
                case 5:
                    processController();
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
            System.out.println("[1]직원 등록 [2]직원 조회 [3]돌아가기");
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

    private void workOrderController() {
        System.out.println("공정 등록을 시작합니다.");
        List<Product> list = productDao.selectProduct();
        System.out.println("제품ID \t 제품명");
        for (Product product : list) {
            System.out.println(product.getProduct_id() + " \t " + product.getProduct_name());
        }
        WorkOrder workOrder = new WorkOrder();
        System.out.println("등록할 제품의 id를 입력해 주세요.");
        System.out.print(">> ");
        workOrder.setProduct_id(SC.nextInt());
        System.out.println("생산할 수량을 입력해 주세요.");
        System.out.print(">> ");
        workOrder.setQuantity(SC.nextInt());
        workOrder.setStatus(Status.IN_PROGRESS);
        boolean result = workOrderDao.insertWorkOrder(workOrder);
        System.out.println(result ? "공정 등록이 완료 되었습니다.\n공정 관리에서 프로세스를 등록해 주세요." : "등록 실패");
    }

    private void processController() {
        while (true) {
            System.out.println("[1]프로세스 등록 [2]공정 조회 [3]공정 취소 [4]돌아가기");
            System.out.print(">> ");
            if(SC.hasNextInt()) {
                switch (SC.nextInt()) {
                    case 1:
                        System.out.println("프로세스 등록을 시작합니다.");
                        List<Wp> list = workOrderDao.selectInProgressWorkOrder();
                        System.out.println("작업ID \t 제품명");
                        for (Wp wp : list) {
                            System.out.println(wp.getWork_order_id() + " \t " + wp.getProduct_name());
                        }
                        System.out.println("프로세스를 등록 할 작업ID를 입력해 주세요.");
                        System.out.print(">> ");
                        int inputId = SC.nextInt();
                        if(workOrderDao.searchWorkOrderId(list, inputId)) {
                            boolean result = processDao.insertProcess(inputId);
                            System.out.println(result ? "프로세스 등록이 완료 되었습니다." : "");
                        }
                        break;
                    case 2:
                        List<ProcessSel> list2 = processDao.selectProcess();
                        for (ProcessSel processSel : list2) {
                            System.out.println(processSel);
                        }
                        break;
                    case 3:
                        List<Wp> list3 = workOrderDao.selectInProgressWorkOrder();
                        System.out.println("작업ID \t 제품명 \t\t\t 생산수량 \t 작업상태");
                        for (Wp wp : list3) {
                            System.out.print(wp.getWork_order_id() + " \t " + wp.getProduct_name() + " \t " + wp.getQuantity() + " \t\t " + wp.getStatus() + "\n");
                        }
                        System.out.println("취소할 작업ID를 입력해 주세요");
                        System.out.print(">> ");
                        int inputId2 = SC.nextInt();
                        if(workOrderDao.searchWorkOrderId(list3, inputId2)) {
                            workOrderDao.cancelWorkOrder(inputId2);
                            System.out.println("작업이 취소 되었습니다.");
                            return;
                        }
                        break;
                    case 4:
                        return;
                    default:
                        System.out.println("잘못 입력하셨습니다.");
                        break;
                }
            } else {
                System.out.println("잘못 입력하셨습니다.");
                SC.next();
            }
        }
    }

    private void shipmentController() {
        while (true) {
            // 1번 출하등록 2번 출하내역조회 3번 돌아가기
            // 주의 - 출하등록 시 어떤 product_id에 대한 출하인지 사용자에게 먼저 입력 받아야함
            // 추가사항 - 출하 등록 수정
            //           inventory 테이블에 임시데이터 추가 후
            //           실제 완제품 재고보다 출하 수량이 많을 경우 출력 문으로 재고가 부족합니다. 를 출력 후
            //           출하 등록을 하지 않고 반환
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
                    List<Sp> list2 = shipmentDao.selectShipment();
                    for (Sp sp : list2) {
                        System.out.println(sp);
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

