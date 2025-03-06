CREATE TABLE Patient (
    id INTEGER PRIMARY KEY,
    name TEXT NOT NULL,
    age INTEGER,
    gender TEXT CHECK(gender IN ('Male', 'Female', 'Other'))
);

CREATE TABLE Disease (
    ICD_10_ID INTEGER PRIMARY KEY,
    description TEXT NOT NULL
);

CREATE TABLE Visits (
    visit_token INTEGER PRIMARY KEY,
    patient_id INTEGER,
    ICD_10_ID INTEGER,
    visit_date TEXT,
    prescribed TEXT,
    FOREIGN KEY (patient_id) REFERENCES Patient(id),
    FOREIGN KEY (ICD_10_ID) REFERENCES Disease(ICD_10_ID)
);
