-- H2 compatible schema for wholesale warehouse

DROP TABLE IF EXISTS stockmovements;
DROP TABLE IF EXISTS salesorderitems;
DROP TABLE IF EXISTS purchaseorderitems;
DROP TABLE IF EXISTS salesorders;
DROP TABLE IF EXISTS purchaseorders;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS products;
DROP TABLE IF EXISTS warehouses;
DROP TABLE IF EXISTS employees;
DROP TABLE IF EXISTS clients;
DROP TABLE IF EXISTS suppliers;
DROP TABLE IF EXISTS categories;

CREATE TABLE categories (
    CategoryID INT AUTO_INCREMENT PRIMARY KEY,
    CategoryName VARCHAR(100) NOT NULL UNIQUE
);

CREATE TABLE clients (
    ClientID INT AUTO_INCREMENT PRIMARY KEY,
    ClientName VARCHAR(150) NOT NULL,
    Phone VARCHAR(30),
    Email VARCHAR(100),
    Address VARCHAR(255)
);

CREATE TABLE employees (
    EmployeeID INT AUTO_INCREMENT PRIMARY KEY,
    FullName VARCHAR(150) NOT NULL,
    Position VARCHAR(100),
    Phone VARCHAR(30)
);

CREATE TABLE suppliers (
    SupplierID INT AUTO_INCREMENT PRIMARY KEY,
    SupplierName VARCHAR(150) NOT NULL,
    Phone VARCHAR(30),
    Email VARCHAR(100),
    Address VARCHAR(255)
);

CREATE TABLE warehouses (
    WarehouseID INT AUTO_INCREMENT PRIMARY KEY,
    WarehouseName VARCHAR(150) NOT NULL,
    Address VARCHAR(255)
);

CREATE TABLE products (
    ProductID INT AUTO_INCREMENT PRIMARY KEY,
    ProductName VARCHAR(150) NOT NULL,
    SKU VARCHAR(50) NOT NULL UNIQUE,
    Unit VARCHAR(30),
    PurchasePrice DECIMAL(10, 2) NOT NULL,
    SalePrice DECIMAL(10, 2) NOT NULL,
    MinStock INT DEFAULT 0,
    CategoryID INT NOT NULL,
    FOREIGN KEY (CategoryID) REFERENCES categories(CategoryID)
);

CREATE TABLE purchaseorders (
    PurchaseOrderID INT AUTO_INCREMENT PRIMARY KEY,
    SupplierID INT NOT NULL,
    EmployeeID INT NOT NULL,
    OrderDate DATE NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (SupplierID) REFERENCES suppliers(SupplierID),
    FOREIGN KEY (EmployeeID) REFERENCES employees(EmployeeID)
);

CREATE TABLE purchaseorderitems (
    PurchaseOrderItemID INT AUTO_INCREMENT PRIMARY KEY,
    PurchaseOrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    PurchasePrice DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (PurchaseOrderID) REFERENCES purchaseorders(PurchaseOrderID),
    FOREIGN KEY (ProductID) REFERENCES products(ProductID)
);

CREATE TABLE salesorders (
    SalesOrderID INT AUTO_INCREMENT PRIMARY KEY,
    ClientID INT NOT NULL,
    EmployeeID INT NOT NULL,
    OrderDate DATE NOT NULL,
    Status VARCHAR(50) NOT NULL,
    FOREIGN KEY (ClientID) REFERENCES clients(ClientID),
    FOREIGN KEY (EmployeeID) REFERENCES employees(EmployeeID)
);

CREATE TABLE salesorderitems (
    SalesOrderItemID INT AUTO_INCREMENT PRIMARY KEY,
    SalesOrderID INT NOT NULL,
    ProductID INT NOT NULL,
    Quantity INT NOT NULL,
    SalePrice DECIMAL(10, 2) NOT NULL,
    FOREIGN KEY (SalesOrderID) REFERENCES salesorders(SalesOrderID),
    FOREIGN KEY (ProductID) REFERENCES products(ProductID)
);

CREATE TABLE stockmovements (
    StockMovementID INT AUTO_INCREMENT PRIMARY KEY,
    ProductID INT NOT NULL,
    WarehouseID INT NOT NULL,
    MovementDate DATE NOT NULL,
    MovementType VARCHAR(10) NOT NULL,
    Quantity INT NOT NULL,
    FOREIGN KEY (ProductID) REFERENCES products(ProductID),
    FOREIGN KEY (WarehouseID) REFERENCES warehouses(WarehouseID),
    CHECK (MovementType IN ('IN', 'OUT'))
);

CREATE TABLE users (
    UserID INT AUTO_INCREMENT PRIMARY KEY,
    EmployeeID INT NOT NULL,
    Username VARCHAR(100) NOT NULL UNIQUE,
    Role VARCHAR(100) NOT NULL,
    FOREIGN KEY (EmployeeID) REFERENCES employees(EmployeeID)
);
