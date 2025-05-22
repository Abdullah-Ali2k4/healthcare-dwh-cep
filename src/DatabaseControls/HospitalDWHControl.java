package DatabaseControls;

import Entities.DWH_Entity.*;
import Entities.PatientRecordEntities.Patient;

import java.sql.*;
import java.util.ArrayList;

public class HospitalDWHControl {

    private static final String URL = "jdbc:postgresql://localhost:5432/dwh_hospital";
    private static final String USER = "postgres";
    private static final String PASSWORD = "secure#123";

    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static void insertDimPatientBatch(ArrayList<Patient> patients) {
        String query = "INSERT INTO dim_patient (patient_id, full_name, dob, gender) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (Patient patient : patients) {
                statement.setInt(1, patient.getId());
                statement.setString(2, patient.getName());
                statement.setDate(3, patient.getDOB());
                statement.setString(4, patient.getGender());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into dim_patient successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDimSupplierBatch(ArrayList<Supplier_Dim> suppliers) {
        String query = "INSERT INTO dim_supplier (id, name, contact_info, address) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (Supplier_Dim supplier : suppliers) {
                statement.setInt(1, supplier.getId());
                statement.setString(2, supplier.getName());
                statement.setString(3, supplier.getContact_info());
                statement.setString(4, supplier.getAddress());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into dim_supplier successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDimMedicationBatch(ArrayList<Med_dim> meds) {
        String query = "INSERT INTO dim_medication (med_id, med_name, supplier_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (Med_dim med : meds) {
                statement.setInt(1, med.getMed_id());
                statement.setString(2, med.getMed_name());
                statement.setInt(3, med.getSupplier_id());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into dim_medication successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertDimOutbreakBatch(ArrayList<Dim_Outbreak> outbreaks) {
        String query = "INSERT INTO dim_outbreak (disease_name, outbreak_monthyear, zip, number_of_outbreak) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (Dim_Outbreak outbreak : outbreaks) {
                statement.setString(1, outbreak.getDiseaseName());
                statement.setString(2, outbreak.getOutbreakMonthYear());
                statement.setString(3, outbreak.getZip());
                statement.setInt(4, outbreak.getNumberOfOutbreak());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into dim_outbreak successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void insertFactVisitBatch(ArrayList<Visit_Dim> visits) {
        String query = "INSERT INTO fact_visit (visit_id, patient_id, visit_date, total_cost, age_group, diagnosis_name, hospital_id) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (Visit_Dim visit : visits) {
                statement.setInt(1, visit.getVisitId());
                statement.setInt(2, visit.getPatientId());
                statement.setDate(3, visit.getVisitDate());
                statement.setDouble(4, visit.getTotalCost());
                statement.setString(5, visit.getAgeGroup());
                statement.setString(6, visit.getDiagnosisName());
                statement.setInt(7, visit.getHospitalId());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into fact_visit successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertFactVisitPerMonthBatch(ArrayList<NumberOfVisit_Dim> visitCounts) {
        String query = "INSERT INTO fact_visitPerMonth (month_year, number_of_visit, hospital_id) VALUES (?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (NumberOfVisit_Dim visit : visitCounts) {
                statement.setString(1, visit.getMonthYear());
                statement.setInt(2, visit.getNumberOfVisits());
                statement.setInt(3, 1); // Or dynamic if needed
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into fact_visitPerMonth successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void insertFactMedicineBatch(ArrayList<Fact_Medicine> factMeds) {
        String query = "INSERT INTO fact_medicine (med_id, used_stock,current_stock, last_updated) VALUES (?, ?, ?, ?)";
        try (Connection connection = getConnection(); PreparedStatement statement = connection.prepareStatement(query)) {
            connection.setAutoCommit(false);
            for (Fact_Medicine factMedicine : factMeds) {
                statement.setInt(1, factMedicine.getMed_id());
                statement.setInt(2, factMedicine.getCurrent_stock());
                statement.setInt(3, factMedicine.getUsed_stock());
                statement.setDate(4, factMedicine.getLast_updated());
                statement.addBatch();
            }
            statement.executeBatch();
            connection.commit();
            System.out.println("Batch inserted into fact_medicine successfully");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void clearAllDwhTables() {

        String[] tables = {
                "dim_medication",
                "dim_outbreak",
                "dim_patient",
                "dim_supplier",
                "fact_medicine",
                "fact_visit",
                "fact_visitpermonth"
        };
        String sql = "TRUNCATE TABLE " + String.join(", ", tables) + " RESTART IDENTITY CASCADE";

        try (Connection connection = getConnection();
             Statement statement = connection.createStatement()) {

            statement.executeUpdate(sql);
            System.out.println("All DWH tables truncated successfully.");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
