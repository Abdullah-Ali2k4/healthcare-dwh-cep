CREATE TABLE dim_patient (
    patient_id INT PRIMARY KEY,
    full_name TEXT,
    age INT,
    gender TEXT
);

CREATE TABLE dim_hospital (
    hospital_id INT PRIMARY KEY,
    hospital_name TEXT,
    zip TEXT,
    contact_info TEXT,
    address TEXT
);

CREATE TABLE dim_supplier (
    id INT PRIMARY KEY,
    name TEXT,
    contact_info TEXT,
    address TEXT
);

CREATE TABLE dim_medication (
    med_id INT PRIMARY KEY,
    med_name TEXT,
    supplier_id INT REFERENCES dim_supplier(id)
);

CREATE TABLE dim_outbreak (
    outbreak_id INT PRIMARY KEY,
    disease_name TEXT,
    outbreak_date DATE,
    location TEXT,
    zip TEXT,
    number_of_outbreak INT
);
CREATE TABLE fact_visit (
    visit_id SERIAL PRIMARY KEY,
    patient_id INT REFERENCES dim_patient(patient_id),
    visit_date DATE NOT NULL,
    total_cost NUMERIC(10, 2) NOT NULL,
    age_group TEXT,
    diagnosis_name TEXT,
    hospital_id INT REFERENCES dim_hospital(hospital_id)
);

CREATE TABLE fact_medicine (
    med_id INT PRIMARY KEY REFERENCES dim_medication(med_id),
    current_stock INT NOT NULL CHECK (current_stock >= 0),
    used_stock INT NOT NULL CHECK (used_stock >= 0),
    last_updated DATE DEFAULT CURRENT_DATE
);

CREATE TABLE fact_visitPerMonth (
    month TEXT,
    year INT,
    number_of_visit INT,
    hospital_id INT REFERENCES dim_hospital(hospital_id)
);
--CREATE TABLE dim_date (
--    full_date DATE PRIMARY KEY,
--    day INT,
--    month TEXT,
--    year INT,
--);
