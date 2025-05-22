# Healthcare Data Warehouse Implementation

A comprehensive data warehouse solution for healthcare analytics, integrating hospital database systems with public health outbreak data to provide actionable insights for healthcare management.

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [System Architecture](#system-architecture)
- [Data Sources](#data-sources)
- [Installation](#installation)
- [Usage](#usage)
- [ETL Process](#etl-process)
- [Data Warehouse Design](#data-warehouse-design)
- [Analytical Capabilities](#analytical-capabilities)
- [Optimization Techniques](#optimization-techniques)
- [Slowly Changing Dimensions (SCD)](#slowly-changing-dimensions-scd)
- [Performance Considerations](#performance-considerations)
- [Project Structure](#project-structure)
- [Contributing](#contributing)
- [License](#license)

## 🎯 Overview

This project implements a healthcare data warehouse by integrating data from hospital databases and public health outbreak reports. It features a complete ETL pipeline that extracts, transforms, and loads data into a star schema-based warehouse optimized for healthcare analytics.

### Key Capabilities
- **Treatment Cost Analysis** by demographics
- **Medication Stock Management** with trend analysis
- **Disease Outbreak Correlation** with hospital visits
- **Real-time Data Processing** with quality assurance

## ✨ Features

- **Multi-Source Data Integration**: Hospital DB + Public Health CSV data
- **Robust ETL Pipeline**: Complete Extract, Transform, Load workflow
- **Data Quality Management**: Handles missing, dirty, and inconsistent data
- **Star Schema Design**: Optimized for analytical queries
- **Real-time Analytics**: Support for complex healthcare queries
- **Performance Optimization**: Multiple optimization techniques implemented

## 🏗️ System Architecture

```
┌─────────────────┐    ┌─────────────────┐
│   Hospital DB   │    │ Public Health   │
│   (Source)      │    │ CSV Data        │
└─────────┬───────┘    └─────────┬───────┘
          │                      │
          └──────────┬───────────┘
                     │
              ┌──────▼──────┐
              │ ETL Process │
              │             │
              │ • Extract   │
              │ • Transform │
              │ • Load      │
              └──────┬──────┘
                     │
           ┌─────────▼─────────┐
           │  Data Warehouse   │
           │  (Star Schema)    │
           │                   │
           │ • Fact Tables     │
           │ • Dimension Tables│
           └─────────┬─────────┘
                     │
            ┌────────▼────────┐
            │   Analytics     │
            │   & Reporting   │
            └─────────────────┘
```

## 📊 Data Sources

### Hospital Database Schema
- **Patients**: Patient demographics and information
- **Visits**: Hospital visit records
- **Diagnoses**: Medical diagnosis data
- **Medications**: Medication prescriptions and inventory
- **Suppliers**: Medication suppliers
- **Billing**: Treatment cost information

### Public Health Data
- **Disease Outbreaks**: Monthly CSV reports with outbreak information
- **Geographic Data**: Location-based health statistics

## 🚀 Installation

### Prerequisites
- Java 8 or higher
- MySQL/PostgreSQL database
- JDBC drivers
- Maven (for dependency management)

### Setup Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/healthcare-data-warehouse.git
   cd healthcare-data-warehouse
   ```

2. **Database Setup**
   ```sql
   -- Create source database
   CREATE DATABASE hospital_db;
   
   -- Create data warehouse database
   CREATE DATABASE healthcare_dwh;
   ```

3. **Configure Database Connection**
   ```properties
   # Update database connection settings
   db.source.url=jdbc:mysql://localhost:3306/hospital_db
   db.warehouse.url=jdbc:mysql://localhost:3306/healthcare_dwh
   db.username=your_username
   db.password=your_password
   ```

4. **Build and Run**
   ```bash
   mvn clean compile
   mvn exec:java -Dexec.mainClass="com.healthcare.dwh.Main"
   ```

## 💻 Usage

### Running the ETL Process
```java
// Initialize and run complete ETL pipeline
RunETL etlRunner = new RunETL();
etlRunner.executeFullETL();
```

### Generating Sample Data
```java
// Generate random hospital data with quality issues
GenerateHospitalData dataGenerator = new GenerateHospitalData();
dataGenerator.generatePatients(1000);
dataGenerator.generateVisits(5000);
```

### Running Analytics Queries
```java
// Execute all analytical queries
Queries queryEngine = new Queries();

// Query 1: Treatment cost analysis by demographics
queryEngine.runQuery1();

// Query 2: Medication stock trend analysis
ArrayList<ArrayList<Query_Entity>> medicationTrends = queryEngine.query2Data();

// Query 3: Disease outbreak correlation analysis
queryEngine.runQuery3();
```

## 🔄 ETL Process

### Extract Phase
- Connects to hospital database and CSV files
- Retrieves patient, visit, medication, and outbreak data
- Handles multiple data source formats

### Transform Phase
- **Data Quality Handling**: Missing values, format standardization
- **Business Logic Application**: Age grouping, cost calculations
- **Data Standardization**: Date formats, naming conventions
- **Aggregation**: Monthly visit summaries

### Load Phase
- Loads dimension tables first (referential integrity)
- Batch loading for performance optimization
- Null checks and error handling
- Maintains data lineage

## 🏛️ Data Warehouse Design

### Conceptual Design
Star schema with fact tables surrounded by dimension tables for optimal analytical performance.

### Logical Design - Galaxy Schema
Multiple fact tables sharing common dimensions:

**Fact Tables:**
- `fact_visit`: Patient visits with costs and outcomes
- `fact_medication`: Medication inventory and usage
- `fact_visitpermonth`: Pre-aggregated monthly visit counts

**Dimension Tables:**
- `dim_patient`: Patient demographics
- `dim_hospital`: Hospital information
- `dim_diagnosis`: Medical diagnoses
- `dim_medication`: Medication details
- `dim_supplier`: Supplier information
- `dim_date`: Time dimension

## 📈 Analytical Capabilities

The healthcare data warehouse supports three comprehensive analytical scenarios through carefully structured SQL queries, each designed to provide specific healthcare insights:

### Query 1: Treatment Cost Analysis by Demographics

**Purpose**: Analyze average treatment costs across different patient demographics to identify cost patterns, optimize resource allocation, and support evidence-based healthcare decisions.

**Implementation**:
```java
public void runQuery1() {
    String query = "SELECT dim_patient.gender, AVG(fact_visit.total_cost) AS avg_cost, "
            + "fact_visit.age_group, fact_visit.diagnosis_name "
            + "FROM fact_visit "
            + "JOIN dim_patient ON dim_patient.patient_id = fact_visit.patient_id "
            + "GROUP BY fact_visit.age_group, dim_patient.gender, fact_visit.diagnosis_name";

    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String gender = rs.getString("gender");
            double avgCost = rs.getDouble("avg_cost");
            String ageGroup = rs.getString("age_group");
            String diagnosisName = rs.getString("diagnosis_name");

            System.out.println("Diagnosis: " + diagnosisName + "\nGender: " + gender + 
                             "\nAge Group: " + ageGroup + ",\nAverage Cost: " + avgCost + "\n");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

**Key Features**:
- **Multi-dimensional Analysis**: Groups by gender, age group, and diagnosis
- **Cost Optimization**: Identifies high-cost treatment areas
- **Demographic Insights**: Reveals cost variations across patient populations
- **Resource Planning**: Supports budget allocation and cost control strategies

**Sample Output**:
```
Diagnosis: Diabetes Type 2
Gender: Female
Age Group: 65-80
Average Cost: 2,450.75

Diagnosis: Hypertension
Gender: Male
Age Group: 45-65
Average Cost: 1,890.50
```

**Business Value**:
- Identify cost drivers by demographic segments
- Target cost reduction initiatives
- Support insurance premium calculations
- Enable evidence-based resource allocation

---

### Query 2: Medication Stock Level Analysis & Trend Monitoring

**Purpose**: Track medication inventory changes over time, identify consumption patterns, and generate replenishment alerts to optimize pharmacy operations and prevent stockouts.

**Implementation**:
```java
// Step 1: Extract raw medication data
private ArrayList<Query_Entity> runQuery2() {
    String query = """
    SELECT fact_medicine.med_id,
           dim_medication.med_name,
           fact_medicine.used_stock,
           fact_medicine.current_stock,
           fact_medicine.last_updated
    FROM fact_medicine
    JOIN dim_medication
      ON fact_medicine.med_id = dim_medication.med_id;
    """;
    
    ArrayList<Query_Entity> queries = new ArrayList<>();
    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            int medId = rs.getInt("med_id");
            String medName = rs.getString("med_name");
            int usedStock = rs.getInt("used_stock");
            int currentStock = rs.getInt("current_stock");
            String lastUpdated = rs.getString("last_updated");

            queries.add(new Query_Entity(medId, medName, usedStock, currentStock, lastUpdated));
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return queries;
}

// Step 2: Process and group data for trend analysis
public ArrayList<ArrayList<Query_Entity>> query2Data() {
    ArrayList<Query_Entity> arrayList = runQuery2();
    ArrayList<ArrayList<Query_Entity>> query = new ArrayList<>();
    HashSet<Integer> visitedMedIds = new HashSet<>();

    for (Query_Entity entity : arrayList) {
        int medId = entity.getMedId();
        if (visitedMedIds.contains(medId)) continue;

        ArrayList<Query_Entity> group = new ArrayList<>();
        for (Query_Entity check : arrayList) {
            if (check.getMedId() == medId) {
                group.add(check);
            }
        }

        // Only include medications with multiple time periods
        HashSet<String> uniqueDates = new HashSet<>();
        for (Query_Entity e : group) {
            uniqueDates.add(e.getLastUpdated());
        }

        if (uniqueDates.size() > 1) {
            query.add(group);
        }
        visitedMedIds.add(medId);
    }

    // Display trend analysis
    for (ArrayList<Query_Entity> group : query) {
        System.out.println("Medication Trend Analysis:\n");
        for (Query_Entity q : group) {
            System.out.println("MedID: " + q.getMedId() + "\nMedicine Name: " + q.getMedName() + 
                             "\nUsed Stock: " + q.getUsedStock() + "\nCurrent Stock: " + q.getCurrentStock() + 
                             "\nLastUpdated: " + q.getLastUpdated() + "\n");
        }
        System.out.println("\n-----------\n");
    }
    return query;
}
```

**Advanced Features**:
- **Temporal Grouping**: Groups medication records by ID across different time periods
- **Trend Detection**: Identifies consumption patterns over time
- **Stock Monitoring**: Tracks current vs. used stock levels
- **Automated Alerts**: Flags medications with significant consumption changes

**Sample Output**:
```
Medication Trend Analysis:

MedID: 101
Medicine Name: Insulin
Used Stock: 45
Current Stock: 155
LastUpdated: 2024-03-15

MedID: 101
Medicine Name: Insulin
Used Stock: 67
Current Stock: 133
LastUpdated: 2024-04-15
```

**Business Intelligence**:
- **Consumption Forecasting**: Predict future medication needs
- **Inventory Optimization**: Maintain optimal stock levels
- **Cost Management**: Reduce waste and prevent stockouts
- **Supply Chain Planning**: Coordinate with suppliers based on trends

---

### Query 3: Disease Outbreak & Hospital Visit Correlation Analysis

**Purpose**: Establish correlations between local disease outbreaks and hospital visit patterns to support public health planning, resource allocation, and emergency preparedness.

**Implementation**:
```java
public void runQuery3() {
    String query = "SELECT dim_outbreak.disease_name, dim_outbreak.outbreak_monthyear, "
            + "dim_outbreak.zip AS outbreak_zip, fact_visitPerMonth.number_of_visit, "
            + "dim_outbreak.number_of_outbreak "
            + "FROM dim_outbreak "
            + "JOIN fact_visitPerMonth ON dim_outbreak.outbreak_monthyear = fact_visitPerMonth.month_year "
            + "ORDER BY fact_visitPerMonth.number_of_visit DESC";

    try (Connection connection = getConnection();
         PreparedStatement stmt = connection.prepareStatement(query);
         ResultSet rs = stmt.executeQuery()) {

        while (rs.next()) {
            String diseaseName = rs.getString("disease_name");
            String outbreakMonthYear = rs.getString("outbreak_monthyear");
            String outbreakZip = rs.getString("outbreak_zip");
            int numberOfVisit = rs.getInt("number_of_visit");
            int numberOfOutbreak = rs.getInt("number_of_outbreak");

            System.out.println("Disease: " + diseaseName + "\nOutbreak Month: " + outbreakMonthYear
                    + "\nZip: " + outbreakZip + "\nVisits: " + numberOfVisit
                    + "\nOutbreaks: " + numberOfOutbreak + "\n\n");
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
}
```

**Analytical Components**:
- **Temporal Correlation**: Links outbreak timing with visit patterns
- **Geographic Analysis**: Associates zip codes with outbreak locations
- **Volume Impact**: Measures visit surge during outbreak periods
- **Multi-disease Tracking**: Monitors various disease outbreak patterns

**Sample Output**:
```
Disease: COVID-19
Outbreak Month: 2024-03
Zip: 12345
Visits: 1,247
Outbreaks: 15

Disease: Influenza
Outbreak Month: 2024-02
Zip: 12346
Visits: 892
Outbreaks: 8
```

**Strategic Applications**:
- **Emergency Preparedness**: Anticipate hospital capacity needs during outbreaks
- **Resource Allocation**: Deploy staff and equipment based on outbreak severity
- **Public Health Policy**: Inform containment strategies and health advisories
- **Predictive Modeling**: Develop early warning systems for future outbreaks

---

## 🔍 Query Performance & Optimization

### Database Configuration
```java
private static final String URL = "jdbc:postgresql://localhost:5432/dwh_hospital";
private static final String USER = "postgres";
private static final String PASSWORD = "secure#123";

private static Connection getConnection() throws SQLException {
    return DriverManager.getConnection(URL, USER, PASSWORD);
}
```

### Performance Enhancements
1. **Prepared Statements**: All queries use PreparedStatement for optimal performance
2. **Strategic Joins**: Direct joins between fact and dimension tables
3. **Result Ordering**: ORDER BY clauses prioritize high-impact results
4. **Connection Management**: Proper resource cleanup with try-with-resources
5. **Batch Processing**: Query 2 implements efficient batch data processing

## ⚡ Optimization Techniques

### Schema-Level Optimizations
- Star schema implementation for analytical performance
- Pre-aggregated fact tables (monthly visit summaries)
- Denormalized dimensions to reduce joins
- Strategic foreign key relationships

### Data Storage Optimizations
- Appropriate data types for storage efficiency
- Date standardization for better performance
- Default values for missing data handling

### Query Performance Optimizations
- Direct join paths between fact and dimension tables
- Pre-calculated derived attributes (age_group, total_cost)
- Temporal analysis support with proper indexing

### ETL Process Optimizations
- Batch processing for reduced database round trips
- Parallel processing where applicable
- Incremental loading strategies

## 📊 Performance Considerations

- **Strategic Joining**: Direct joins without complex multi-table operations
- **Effective Grouping**: Multi-dimensional grouping for meaningful segments
- **Result Optimization**: Sorting by impact for prioritized analysis
- **Prepared Statements**: Optimized database interactions
- **Memory Management**: Efficient handling of large datasets

## 📁 Project Structure

```
healthcare-data-warehouse/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   ├── data/
│   │   │   │   ├── generation/
│   │   │   │   │   ├── GenerateData.java
│   │   │   │   │   ├── GenerateHospitalData.java
│   │   │   │   │   └── RandomData.java
│   │   │   │   └── controls/
│   │   │   │       ├── OperationalDatabaseControl.java
│   │   │   │       └── ExtractPublicHealthData.java
│   │   │   ├── etl/
│   │   │   │   ├── RunETL.java
│   │   │   │   ├── Extract.java
│   │   │   │   ├── Transform.java
│   │   │   │   ├── Load.java
│   │   │   │   └── scd/
│   │   │   │       ├── SCDExtractor.java
│   │   │   │       ├── SCDTransformer.java
│   │   │   │       └── SCDLoader.java
│   │   │   ├── utils/
│   │   │   │   └── FiltersHelperMethod.java
            │   │   │   ├── analytics/
│   │   │   │   │   ├── AnalyticsEngine.java
│   │   │   │   │   └── Queries.java
│   │   │   │   └── entities/
│   │   │   │       └── Query_Entity.java
│   │   └── resources/
│   │       ├── config/
│   │       │   └── database.properties
│   │       └── sql/
│   │           ├── schema/
│   │                       └── queries/
│               ├── analytics/
│               │   ├── treatment_cost_analysis.sql
│               │   ├── medication_stock_analysis.sql
│               │   └── outbreak_correlation.sql
│               └── scd/
│                   ├── scd_type1_updates.sql
│                   ├── scd_type2_inserts.sql
│                   └── scd_monitoring.sql
├── docs/
│   └── DWH_report.pdf
├── README.md
├── pom.xml
└── .gitignore
```

## 🤝 Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📝 Design Assumptions

### Source Database Assumptions
- Patient can only be prescribed one medicine at a time
- Hospital billing system may contain inconsistent date formats

### Data Warehouse Assumptions
- Detailed billing information not required for analysis
- System designed for scalability with multiple hospital data integration
- Base procedure and medication cost defaults to 1000 if missing
- Missing patient names default to "Anonymous"

## 📄 License

This project is part of an academic assignment for the Data Warehousing course at Mehran UET, Department of Software Engineering.

**Student**: Syed Abdullah Ali (23SW082)  
**Course**: Data Warehousing  
**Institution**: Mehran University of Engineering & Technology

---
