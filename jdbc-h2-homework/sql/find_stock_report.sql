SELECT
    p.ProductID,
    p.ProductName,
    p.SKU,
    SUM(
            CASE
                WHEN sm.MovementType = 'IN' THEN sm.Quantity
                WHEN sm.MovementType = 'OUT' THEN -sm.Quantity
                ELSE 0
                END
    ) AS CurrentStock,
    p.MinStock
FROM Products p
         LEFT JOIN StockMovements sm ON p.ProductID = sm.ProductID
GROUP BY p.ProductID, p.ProductName, p.SKU, p.MinStock
ORDER BY p.ProductID;