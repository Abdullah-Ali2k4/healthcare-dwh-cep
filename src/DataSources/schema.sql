--CREATE TABLE Patient (
--    id INTEGER PRIMARY KEY,
--    name TEXT ,
--    date_of_birth text,
--    gender TEXT CHECK(gender IN ('Male', 'Female', 'Other'))
--);
--
----CREATE TABLE if not exist Disease (
----    ICD_10_ID INTEGER PRIMARY KEY AUTOINCREMENT,
----    ICD_10_Code TEXT NOT NULL,
----    description TEXT NOT NULL
----);
--
----CREATE TABLE if not exist Visits (
----    visit_token INTEGER PRIMARY KEY,
----    patient_id INTEGER,
----    ICD_10_ID INTEGER,
----    visit_date TEXT,
----    prescribed TEXT,
----    FOREIGN KEY (patient_id) REFERENCES Patient(id),
----    FOREIGN KEY (ICD_10_ID) REFERENCES Disease(ICD_10_ID)
----);
--
--CREATE TABLE Supplier (
--    supplier_id INTEGER PRIMARY KEY,
--    name TEXT ,
--    contact_info TEXT,
--    address TEXT
--);
--
--CREATE TABLE if not exist Pharmacy_Inventory (
--    medicine_id INTEGER PRIMARY KEY,
--    name TEXT NOT NULL,
--    stock_level INTEGER NOT NULL CHECK(stock_level >= 0),
--    supplier_id INTEGER NOT NULL,
--    dispense_date DATE NOT NULL,
--    FOREIGN KEY (supplier_id) REFERENCES Supplier(supplier_id)
--);
--
--CREATE TABLE  Prescription (
--    prescription_id INTEGER PRIMARY KEY,
--    patient_visit INTEGER NOT NULL,
--    medicine_id INTEGER NOT NULL,
--    quantity INTEGER,
--    FOREIGN KEY (patient_visit) REFERENCES Visits(visit_token),
--    FOREIGN KEY (medicine_id) REFERENCES Pharmacy_Inventory(medicine_id)
--);
--
--CREATE TABLE if not exist Billing (
--    bill_id INTEGER PRIMARY KEY,
--    visit_id INTEGER NOT NULL,
--    procedure_cost FLOAT CHECK(procedure_cost >= 0),
--    medication_cost FLOAT CHECK(medication_cost >= 0),
--    total_cost FLOAT GENERATED ALWAYS AS (procedure_cost + medication_cost) STORED,
--    insurance_claim_status TEXT CHECK(insurance_claim_status IN ('Pending', 'Approved', 'Rejected')),
--    FOREIGN KEY (visit_id) REFERENCES Visits(visit_token)
--);
--
--
--CREATE TRIGGER update_stock_after_prescription
--AFTER INSERT ON Prescription
--FOR EACH ROW
--BEGIN
--    UPDATE Pharmacy_Inventory
--    SET stock_level = stock_level - NEW.quantity
--    WHERE medicine_id = NEW.medicine_id;
--END;
--
--CREATE TRIGGER prevent_negative_stock
--BEFORE INSERT ON Prescription
--FOR EACH ROW
--BEGIN
--    SELECT CASE
--        WHEN (SELECT stock_level FROM Pharmacy_Inventory WHERE medicine_id = NEW.medicine_id) < NEW.quantity
--        THEN RAISE(ABORT, 'Not enough stock available')
--    END;
--END;

--
PRAGMA foreign_keys = OFF;
DELETE FROM billing;
DELETE FROM visits;
DELETE FROM patient;
DELETE FROM prescription;
PRAGMA foreign_keys = ON;

--SELECT patient.id, patient.date_of_birth,visits.visit_token,
--        visits.visit_date, disease.description,billing.procedure_cost,
--        billing.medication_cost,billing.total_cost
--        FROM visits
--        JOIN billing ON visits.visit_token = billing.visit_id
--        JOIN patient ON visits.patient_id = patient.id
--        JOIN disease ON visits.ICD_10_ID = disease.ICD_10_ID;

--SELECT prescription.medicine_id,pharmacy_inventory.name,SUM(prescription.quantity),
--        pharmacy_inventory.stock_level,pharmacy_inventory.dispense_date
--        FROM prescription
--        JOIN pharmacy_inventory ON prescription.medicine_id=pharmacy_inventory.medicine_id
--        GROUP BY prescription.medicine_id;

