DELIMITER //

CREATE PROCEDURE findContactInfo(IN CustomerID INT, OUT contact VARCHAR(255))
BEGIN
    SELECT Contact_Information INTO contact
    FROM CUSTOMER 
    WHERE Customer_ID = CustomerID 
    LIMIT 1;
END //

DELIMITER ;