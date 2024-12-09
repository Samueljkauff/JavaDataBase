
DELIMITER //

CREATE PROCEDURE findBillingInfo(IN CustomerID INT, OUT BillingDetails VARCHAR(255))
BEGIN
    SELECT Billing_Details INTO BillingDetails 
    FROM CUSTOMER 
    WHERE Customer_ID = CustomerID 
    LIMIT 1;
END //

DELIMITER ;