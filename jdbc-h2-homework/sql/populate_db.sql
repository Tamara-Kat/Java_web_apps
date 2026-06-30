-- H2 compatible data for wholesale warehouse
-- Generated from MariaDB dump; ordered by foreign-key dependencies

-- categories
INSERT INTO categories VALUES (5,'Господарські товари');
INSERT INTO categories VALUES (4,'Електроніка');
INSERT INTO categories VALUES (3,'Канцелярія');
INSERT INTO categories VALUES (1,'Побутова хімія');
INSERT INTO categories VALUES (2,'Продукти харчування');

-- clients
INSERT INTO clients VALUES (1,'Маркет Плюс','+380931010101','market.plus@example.com','м. Київ, просп. Перемоги, 20');
INSERT INTO clients VALUES (2,'Офіс Центр','+380992020202','office.center@example.com','м. Львів, вул. Городоцька, 11');
INSERT INTO clients VALUES (3,'Retail Group','+380673030303','retail.group@example.com','м. Дніпро, вул. Центральна, 8');
INSERT INTO clients VALUES (4,'Мережа Крамниць','+380504040404','shops.network@example.com','м. Харків, вул. Торгова, 15');

-- employees
INSERT INTO employees VALUES (1,'Іван Петренко','Комірник','+380501234567');
INSERT INTO employees VALUES (2,'Олена Коваль','Менеджер з продажів','+380671234567');
INSERT INTO employees VALUES (3,'Марина Шевченко','Бухгалтер','+380631234567');
INSERT INTO employees VALUES (4,'Андрій Мельник','Адміністратор системи','+380991234567');
INSERT INTO employees VALUES (5,'Сергій Бондар','Комірник','+380681112233');

-- suppliers
INSERT INTO suppliers VALUES (1,'ТОВ Постач-Сервіс','+380501112233','supply.service@example.com','м. Київ, вул. Складська, 10');
INSERT INTO suppliers VALUES (2,'ОптТрейд Україна','+380672223344','opttrade@example.com','м. Львів, вул. Промислова, 5');
INSERT INTO suppliers VALUES (3,'Global Goods LLC','+380633334455','global.goods@example.com','м. Одеса, вул. Логістична, 14');
INSERT INTO suppliers VALUES (4,'ПромОпт Захід','+380684445566','promopt@example.com','м. Тернопіль, вул. Виробнича, 21');

-- warehouses
INSERT INTO warehouses VALUES (1,'Основний склад','м. Київ, складська зона А');
INSERT INTO warehouses VALUES (2,'Резервний склад','м. Київ, складська зона Б');
INSERT INTO warehouses VALUES (3,'Склад швидкого відвантаження','м. Київ, рампа 3');

-- products
INSERT INTO products VALUES (1,'Пральний порошок 5 кг','SKU-CH-001','шт',180.00,245.00,20,1);
INSERT INTO products VALUES (2,'Засіб для миття посуду 1 л','SKU-CH-002','шт',45.00,70.00,30,1);
INSERT INTO products VALUES (3,'Кава зернова 1 кг','SKU-FO-001','шт',280.00,390.00,15,2);
INSERT INTO products VALUES (4,'Цукор 25 кг','SKU-FO-002','мішок',850.00,990.00,10,2);
INSERT INTO products VALUES (5,'Папір офісний A4','SKU-OF-001','пачка',135.00,185.00,25,3);
INSERT INTO products VALUES (6,'Ручка кулькова синя','SKU-OF-002','шт',5.00,9.00,200,3);
INSERT INTO products VALUES (7,'USB-кабель Type-C','SKU-EL-001','шт',55.00,95.00,50,4);
INSERT INTO products VALUES (8,'Електрочайник 1.7 л','SKU-EL-002','шт',420.00,620.00,8,4);
INSERT INTO products VALUES (9,'Мішки для сміття 120 л','SKU-HH-001','уп',60.00,95.00,40,5);
INSERT INTO products VALUES (10,'Рукавички господарські','SKU-HH-002','пара',18.00,35.00,60,5);

-- purchaseorders
INSERT INTO purchaseorders VALUES (1,1,1,'2025-05-01','Completed');
INSERT INTO purchaseorders VALUES (2,2,1,'2025-05-04','Completed');
INSERT INTO purchaseorders VALUES (3,3,5,'2025-05-08','Completed');
INSERT INTO purchaseorders VALUES (4,4,5,'2025-05-18','Completed');

-- salesorders
INSERT INTO salesorders VALUES (1,1,2,'2025-05-10','Completed');
INSERT INTO salesorders VALUES (2,2,2,'2025-05-12','Completed');
INSERT INTO salesorders VALUES (3,3,2,'2025-05-15','Completed');
INSERT INTO salesorders VALUES (4,4,2,'2025-05-20','New');
INSERT INTO salesorders VALUES (5,1,2,'2025-06-02','Completed');

-- purchaseorderitems
INSERT INTO purchaseorderitems VALUES (1,1,1,100,180.00);
INSERT INTO purchaseorderitems VALUES (2,1,2,150,45.00);
INSERT INTO purchaseorderitems VALUES (3,1,9,120,60.00);
INSERT INTO purchaseorderitems VALUES (4,2,3,50,280.00);
INSERT INTO purchaseorderitems VALUES (5,2,4,30,850.00);
INSERT INTO purchaseorderitems VALUES (6,2,5,120,135.00);
INSERT INTO purchaseorderitems VALUES (7,3,6,500,5.00);
INSERT INTO purchaseorderitems VALUES (8,3,7,200,55.00);
INSERT INTO purchaseorderitems VALUES (9,3,8,40,420.00);
INSERT INTO purchaseorderitems VALUES (10,4,10,180,18.00);

-- salesorderitems
INSERT INTO salesorderitems VALUES (1,1,1,20,245.00);
INSERT INTO salesorderitems VALUES (2,1,3,10,390.00);
INSERT INTO salesorderitems VALUES (3,1,6,100,9.00);
INSERT INTO salesorderitems VALUES (4,2,5,40,185.00);
INSERT INTO salesorderitems VALUES (5,2,7,25,95.00);
INSERT INTO salesorderitems VALUES (6,3,2,30,70.00);
INSERT INTO salesorderitems VALUES (7,3,4,5,990.00);
INSERT INTO salesorderitems VALUES (8,3,9,35,95.00);
INSERT INTO salesorderitems VALUES (9,4,8,4,620.00);
INSERT INTO salesorderitems VALUES (10,4,10,30,35.00);
INSERT INTO salesorderitems VALUES (11,5,1,15,245.00);
INSERT INTO salesorderitems VALUES (12,5,5,20,185.00);
INSERT INTO salesorderitems VALUES (13,5,6,80,9.00);

-- stockmovements
INSERT INTO stockmovements VALUES (1,1,1,'2025-05-01','IN',100);
INSERT INTO stockmovements VALUES (2,2,1,'2025-05-01','IN',150);
INSERT INTO stockmovements VALUES (3,9,1,'2025-05-01','IN',120);
INSERT INTO stockmovements VALUES (4,3,1,'2025-05-04','IN',50);
INSERT INTO stockmovements VALUES (5,4,1,'2025-05-04','IN',30);
INSERT INTO stockmovements VALUES (6,5,2,'2025-05-04','IN',120);
INSERT INTO stockmovements VALUES (7,6,2,'2025-05-08','IN',500);
INSERT INTO stockmovements VALUES (8,7,2,'2025-05-08','IN',200);
INSERT INTO stockmovements VALUES (9,8,2,'2025-05-08','IN',40);
INSERT INTO stockmovements VALUES (10,10,3,'2025-05-18','IN',180);
INSERT INTO stockmovements VALUES (11,1,1,'2025-05-10','OUT',20);
INSERT INTO stockmovements VALUES (12,3,1,'2025-05-10','OUT',10);
INSERT INTO stockmovements VALUES (13,6,2,'2025-05-10','OUT',100);
INSERT INTO stockmovements VALUES (14,5,2,'2025-05-12','OUT',40);
INSERT INTO stockmovements VALUES (15,7,2,'2025-05-12','OUT',25);
INSERT INTO stockmovements VALUES (16,2,1,'2025-05-15','OUT',30);
INSERT INTO stockmovements VALUES (17,4,1,'2025-05-15','OUT',5);
INSERT INTO stockmovements VALUES (18,9,1,'2025-05-15','OUT',35);
INSERT INTO stockmovements VALUES (19,8,2,'2025-05-20','OUT',4);
INSERT INTO stockmovements VALUES (20,10,3,'2025-05-20','OUT',30);
INSERT INTO stockmovements VALUES (21,1,1,'2025-06-02','OUT',15);
INSERT INTO stockmovements VALUES (22,5,2,'2025-06-02','OUT',20);
INSERT INTO stockmovements VALUES (23,6,2,'2025-06-02','OUT',80);

-- users
INSERT INTO users VALUES (1,1,'warehouse_worker_1','Комірник');
INSERT INTO users VALUES (2,2,'sales_manager','Менеджер з продажів');
INSERT INTO users VALUES (3,3,'accountant','Бухгалтер');
INSERT INTO users VALUES (4,4,'admin','Адміністратор');
INSERT INTO users VALUES (5,5,'warehouse_worker_2','Комірник');
