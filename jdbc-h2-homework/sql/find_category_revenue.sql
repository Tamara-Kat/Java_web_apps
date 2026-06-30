SELECT
    cat.CategoryName,
    SUM(soi.Quantity) AS TotalQuantity,
    SUM(soi.Quantity * soi.SalePrice) AS TotalRevenue
FROM SalesOrderItems soi
         JOIN Products p ON soi.ProductID = p.ProductID
         JOIN Categories cat ON p.CategoryID = cat.CategoryID
GROUP BY cat.CategoryName
ORDER BY TotalRevenue DESC;