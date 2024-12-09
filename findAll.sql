
DELIMITER //

CREATE PROCEDURE findAll(
    IN CustomerID INT,
    OUT CustomerName VARCHAR(100),
    OUT ContactInfo VARCHAR(255),
    OUT BillingDetails VARCHAR(255)
)
BEGIN
    SELECT Name, Contact_Information, Billing_Details
    INTO CustomerName, ContactInfo, BillingDetails
    FROM CUSTOMER
    WHERE Customer_ID = CustomerID
    LIMIT 1;
END //

DELIMITER ;