SELECT
    p.ProductName,
    SUM(soi.Quantity) AS TotalQuantity,
    SUM(soi.Quantity * soi.SalePrice) AS TotalRevenue
FROM SalesOrderItems soi
         JOIN Products p ON soi.ProductID = p.ProductID
GROUP BY p.ProductName
ORDER BY TotalRevenue DESC
    LIMIT 5;