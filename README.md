-- DATA

DROP TABLE SHIPMENT;
DROP TABLE INVENTORY;
DROP TABLE PERFORMANCE;
DROP TABLE PROCESS;
DROP TABLE BOM_DETAIL;
DROP TABLE BOM;
DROP TABLE WORK_ORDER;
DROP TABLE PRODUCT;
DROP TABLE PART;
DROP TABLE EMPLOYEE;
DROP SEQUENCE PRODUCT_SEQ;
DROP SEQUENCE PART_SEQ;
DROP SEQUENCE BOM_SEQ;
DROP SEQUENCE BOM_DETAIL_SEQ;
DROP SEQUENCE WORK_ORDER_SEQ;
DROP SEQUENCE EMPLOYEE_SEQ;
DROP SEQUENCE process_seq;
DROP SEQUENCE PERFORMANCE_SEQ;
DROP SEQUENCE INVENTORY_SEQ;
DROP SEQUENCE SHIPMENT_SEQ;

-- 제품 테이블
CREATE TABLE PRODUCT (
product_id NUMBER(10) PRIMARY KEY,
product_name VARCHAR2(255) NOT NULL,
model_number VARCHAR2(100) NOT NULL UNIQUE
);
-- 제품별 BOM(자재명세서) 테이블
CREATE TABLE BOM (
bom_id NUMBER(10) PRIMARY KEY,
product_id NUMBER(10) UNIQUE,
CONSTRAINT fk_bom_product FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
ON DELETE CASCADE
);
-- 부품 테이블
CREATE TABLE PART (
part_id NUMBER(10) PRIMARY KEY,
part_name VARCHAR2(255) NOT NULL,
part_number VARCHAR2(100) NOT NULL UNIQUE
);
-- BOM 상세 테이블
CREATE TABLE BOM_DETAIL (
bom_detail_id NUMBER(10) PRIMARY KEY,
bom_id NUMBER(10) NOT NULL,
part_id NUMBER(10) NOT NULL,
quantity NUMBER(5) NOT NULL,
CONSTRAINT fk_bom_detail_bom FOREIGN KEY (bom_id) REFERENCES BOM(bom_id)
ON DELETE CASCADE,
CONSTRAINT fk_bom_detail_part FOREIGN KEY (part_id) REFERENCES PART(part_id)
);
-- 작업지시 테이블
CREATE TABLE WORK_ORDER (
work_order_id NUMBER(10) PRIMARY KEY,
product_id NUMBER(10) NOT NULL,
quantity NUMBER(10) NOT NULL,
start_date TIMESTAMP NOT NULL,
status VARCHAR2(50) NOT NULL,
CONSTRAINT fk_work_order_product FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
ON DELETE CASCADE
);
-- 직원 테이블
CREATE TABLE EMPLOYEE (
employee_id NUMBER(10) PRIMARY KEY,
employee_name VARCHAR2(255) NOT NULL
);
-- 공정 테이블
CREATE TABLE PROCESS (
process_id NUMBER(10) PRIMARY KEY,
work_order_id NUMBER(10) NOT NULL,
process_type VARCHAR2(100) NOT NULL,
start_time TIMESTAMP default sysdate NOT NULL,
end_time TIMESTAMP default sysdate,
employee_id NUMBER(10),
CONSTRAINT fk_process_work_order FOREIGN KEY (work_order_id) REFERENCES WORK_ORDER(work_order_id)
ON DELETE CASCADE,
CONSTRAINT fk_process_employee FOREIGN KEY (employee_id) REFERENCES EMPLOYEE(employee_id)
ON DELETE SET NULL
);
-- 공정 성과(검사 결과) 테이블
CREATE TABLE PERFORMANCE (
performance_id NUMBER(10) PRIMARY KEY,
process_id NUMBER(10) NOT NULL,
good_quantity NUMBER(10) NOT NULL,
defect_quantity NUMBER(10) NOT NULL,
inspection_status VARCHAR2(50) NOT NULL,
inspection_date TIMESTAMP NOT NULL,
CONSTRAINT fk_performance_process FOREIGN KEY (process_id) REFERENCES PROCESS(process_id)
ON DELETE CASCADE
);
-- 재고 테이블
CREATE TABLE INVENTORY (
inventory_id NUMBER(10) PRIMARY KEY,
product_id NUMBER(10) UNIQUE,
current_stock NUMBER(10) NOT NULL,
last_updated TIMESTAMP NOT NULL,
CONSTRAINT fk_inventory_product FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
ON DELETE CASCADE
);
-- 출하 테이블
CREATE TABLE SHIPMENT (
shipment_id NUMBER(10) PRIMARY KEY,
product_id NUMBER(10) NOT NULL,
quantity NUMBER(10) NOT NULL,
shipment_date TIMESTAMP NOT NULL,
CONSTRAINT fk_shipment_product FOREIGN KEY (product_id) REFERENCES PRODUCT(product_id)
);

INSERT INTO PART (part_id, part_name, part_number) VALUES (201, 'AP 칩셋', 'EXYNOS-2300');
INSERT INTO PART (part_id, part_name, part_number) VALUES (202, 'OLED 디스플레이', 'OLED-120HZ');
INSERT INTO PART (part_id, part_name, part_number) VALUES (203, '메인보드', 'MB-SM-S911');
INSERT INTO PART (part_id, part_name, part_number) VALUES (204, '카메라 모듈', 'CAM-TRIPLE-V3');
INSERT INTO PART (part_id, part_name, part_number) VALUES (205, 'Li-Po 배터리', 'BAT-5000MAH');
INSERT INTO PART (part_id, part_name, part_number) VALUES (206, 'Li-ion 배터리', 'BAT-762MAH');
INSERT INTO PART (part_id, part_name, part_number) VALUES (207, '글라스 스트랩', 'STRAP-WATCH-G');

-- 제품 시퀀스
CREATE SEQUENCE PRODUCT_SEQ INCREMENT BY 1 START WITH 104;
 -- 부품 시퀀스
CREATE SEQUENCE PART_SEQ INCREMENT BY 1 START WITH 208;
 -- BOM 시퀀스
CREATE SEQUENCE BOM_SEQ INCREMENT BY 1 START WITH 304;
 -- BOM 상세 시퀀스
CREATE SEQUENCE BOM_DETAIL_SEQ INCREMENT BY 1 START WITH 410;
 -- 작업 지시 시퀀스
CREATE SEQUENCE WORK_ORDER_SEQ INCREMENT BY 1 START WITH 601;
 -- 직원 시퀀스
CREATE SEQUENCE EMPLOYEE_SEQ INCREMENT BY 1 START WITH 501;
 -- 공정 시퀀스
CREATE SEQUENCE PROCESS_SEQ INCREMENT BY 1 START WITH 701;
 -- 성과(검사 결과) 시퀀스
CREATE SEQUENCE PERFORMANCE_SEQ INCREMENT BY 1 START WITH 801;
 -- 재고 시퀀스
CREATE SEQUENCE INVENTORY_SEQ INCREMENT BY 1 START WITH 901;
 -- 출하 시퀀스
CREATE SEQUENCE SHIPMENT_SEQ INCREMENT BY 1 START WITH 1001;

INSERT INTO PRODUCT (product_id, product_name, model_number) VALUES (101, 'Galaxy S25', 'SM-S911N');
INSERT INTO PRODUCT (product_id, product_name, model_number) VALUES (102, 'iPad Pro 11', 'A2759');
INSERT INTO PRODUCT (product_id, product_name, model_number) VALUES (103, 'Apple Watch Ultra', 'A2622');

-- Galaxy S23 BOM
INSERT INTO BOM (bom_id, product_id) VALUES (301, 101);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (401, 301, 201, 100);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (402, 301, 202, 100);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (403, 301, 203, 100);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (404, 301, 204, 100);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (405, 301, 205, 100);
-- iPad Pro 11 BOM
INSERT INTO BOM (bom_id, product_id) VALUES (302, 102);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (406, 302, 202, 100);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (407, 302, 205, 100);
-- Apple Watch Ultra BOM
INSERT INTO BOM (bom_id, product_id) VALUES (303, 103);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (408, 303, 206, 100);
INSERT INTO BOM_DETAIL (bom_detail_id, bom_id, part_id, quantity) VALUES (409, 303, 207, 100);

commit;
