
DELIMITER //

CREATE PROCEDURE findName(IN CustomerID INT, OUT CustomerName VARCHAR(100))
BEGIN
    SELECT Name INTO CustomerName
    FROM CUSTOMER 
    WHERE Customer_ID = CustomerID 
    LIMIT 1;
END //

DELIMITER ;