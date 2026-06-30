SELECT
    so.SalesOrderID AS OrderNumber,
    so.OrderDate,
    c.ClientName,
    e.FullName AS Manager,
    p.ProductName,
    cat.CategoryName,
    soi.Quantity,
    soi.SalePrice AS UnitPrice,
    soi.Quantity * soi.SalePrice AS LineTotal
FROM SalesOrders so
         JOIN Clients c ON so.ClientID = c.ClientID
         JOIN Employees e ON so.EmployeeID = e.EmployeeID
         JOIN SalesOrderItems soi ON so.SalesOrderID = soi.SalesOrderID
         JOIN Products p ON soi.ProductID = p.ProductID
         JOIN Categories cat ON p.CategoryID = cat.CategoryID
ORDER BY so.OrderDate, so.SalesOrderID;