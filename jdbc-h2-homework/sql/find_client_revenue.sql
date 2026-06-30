SELECT
    c.ClientName,
    COUNT(DISTINCT so.SalesOrderID) AS OrdersCount,
    SUM(soi.Quantity) AS TotalUnits,
    SUM(soi.Quantity * soi.SalePrice) AS TotalRevenue
FROM Clients c
         JOIN SalesOrders so ON c.ClientID = so.ClientID
         JOIN SalesOrderItems soi ON so.SalesOrderID = soi.SalesOrderID
GROUP BY c.ClientName
ORDER BY TotalRevenue DESC;