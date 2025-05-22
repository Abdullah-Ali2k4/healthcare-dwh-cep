package DatabaseControls;

import Entities.DWH_Entity.*;
import Entities.PatientRecordEntities.*;

import java.sql.*;
import java.util.ArrayList;
import java.util.Random;

public class OperationalDBControl {
    static Random random = new Random();
    static String url = "jdbc:sqlite:src\\DataSources\\Hospital.db";

    public static boolean addPatientRecord(Patient patient) {
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement patientInsert = connection.prepareStatement("INSERT INTO Patient(id, name, date_of_birth, gender) VALUES (?, ?, ?, ?)");
            patientInsert.setString(2, patient.getName());
            patientInsert.setString(3, patient.getDateOfBirth());
            patientInsert.setString(4, patient.getGender());
            patientInsert.executeUpdate();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean addVisitRecord(Visit visit) {
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement insertVisit = connection.prepareStatement("Insert INTO Visits(patient_id,ICD_10_ID,visit_date) VALUES(?,?,?)");
            int numberOfPatients = connection.createStatement().executeQuery("SELECT COUNT(*) FROM Patient").getInt(1);

            insertVisit.setInt(1, random.nextInt(numberOfPatients)+1);
            insertVisit.setInt(2, random.nextInt(74260));
            insertVisit.setString(3, visit.getVisit_date());
            insertVisit.executeUpdate();
            connection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean addBillingRecord(Billing billing) {
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement billInsert = connection.prepareStatement(
                    "INSERT INTO Billing(visit_id, procedure_cost, medication_cost, insurance_claim_status) VALUES (?, ?, ?, ?)"
            );
            ResultSet numberOfVisits = connection.createStatement().executeQuery("SELECT visit_token FROM Visits WHERE visit_token NOT IN (SELECT visit_id FROM billing) ORDER BY RANDOM() LIMIT 1");
            if (numberOfVisits.next()) {
                billInsert.setInt(1, numberOfVisits.getInt("visit_token"));
            }
            billInsert.setFloat(2, billing.getProcedure_cost());
            billInsert.setFloat(3, billing.getMedication_cost());
            billInsert.setString(4, billing.getInsurance_claim_status());
            billInsert.executeUpdate();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean addSupplierRecord(Supplier supplier) {
        try {
            Connection connection = DriverManager.getConnection(url);
            PreparedStatement supplierInsert = connection.prepareStatement(
                    "INSERT INTO Supplier(name, contact_info, address) VALUES (?, ?, ?)"
            );

            supplierInsert.setString(1, supplier.getName());
            supplierInsert.setString(2, supplier.getContactInfo());
            supplierInsert.setString(3, supplier.getAddress());
            supplierInsert.executeUpdate();
            connection.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    public static boolean addPharmacyInventoryRecord(PharmacyInventory pharmacyInventory) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO Pharmacy_Inventory(name, stock_level, supplier_id, dispense_date) VALUES (?, ?, ?, ?)";
            int numberOfSuppliers = connection.createStatement().executeQuery("SELECT COUNT(*) FROM Supplier").getInt(1);
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, pharmacyInventory.getMedName());
            statement.setInt(2, pharmacyInventory.getStockLevel());
            statement.setInt(3, random.nextInt(numberOfSuppliers) + 1);
            statement.setString(4, pharmacyInventory.getDispenseData());

            statement.executeUpdate();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error inserting into Pharmacy_Inventory: " + e.getMessage());
            return false;
        }
    }

    public static boolean addPrescriptionRecord(Prescription prescription) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "INSERT INTO Prescription(patient_visit, medicine_id, quantity) VALUES (?, ?, ?)";

            int numberOfMedicine = connection.createStatement().executeQuery("SELECT COUNT(*) FROM Pharmacy_Inventory").getInt(1);

            ResultSet numberOfVisits = connection.createStatement().executeQuery("SELECT visit_token FROM Visits WHERE visit_token NOT IN (SELECT patient_visit FROM Prescription) ORDER BY RANDOM() LIMIT 1");


            PreparedStatement statement = connection.prepareStatement(sql);
             if (numberOfVisits.next()) {
                statement.setInt(1, numberOfVisits.getInt("visit_token"));
            }
            statement.setInt(2, random.nextInt(numberOfMedicine) + 1);
            statement.setInt(3, prescription.getMedQuantity());

            statement.executeUpdate();
            connection.close();
            return true;
        } catch (Exception e) {
            System.err.println("Error inserting into Prescription: " + e.getMessage());
            return false;
        }
    }

    public static boolean updatePharmacyInventoryRecord(String name, int level) {
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "UPDATE Pharmacy_Inventory SET STOCK_LEVEL=? WHERE name =?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, level);
            statement.setString(2, name);

            statement.executeUpdate();
            return true;
        } catch (Exception e) {
            System.err.println("Error updating into Pharmacy_Inventory: " + e.getMessage());
            return false;
        }
    }

    static int[] lastLoaded = {0, 0, 0, 0, 0};

    public static ArrayList<Patient> selectPatientRecords() {
        try (Connection connection = DriverManager.getConnection(url)) {
            String sql = "SELECT * FROM patient WHERE id > " + lastLoaded[0] + " ORDER BY id ASC";
            PreparedStatement statement = connection.prepareStatement(sql);
            ArrayList<Patient> patients = new ArrayList<>();
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int patientId = resultSet.getInt("id");
                String patientName = resultSet.getString("name");
                String DOB = resultSet.getString("date_of_birth");
                String gender = resultSet.getString("gender");
                patients.add(new Patient(patientId, patientName, DOB, gender));
                lastLoaded[0] = patientId;  // Store the latest ID
            }
            return patients;
        } catch (Exception e) {
            System.err.println(e.getMessage());
            return null;
        }
    }

    public static ArrayList<Visit_Dim> selectVisitsAndBilling() {
        String sql = """
        SELECT patient.id, patient.date_of_birth, visits.visit_token, visits.visit_date,
               disease.description, billing.total_cost,
               billing.procedure_cost, billing.medication_cost
        FROM visits
        JOIN billing ON visits.visit_token = billing.visit_id
        JOIN patient ON visits.patient_id = patient.id
        JOIN disease ON visits.ICD_10_ID = disease.ICD_10_ID
        WHERE visits.visit_token > """ + lastLoaded[1] + " ORDER BY visits.visit_token ASC";

        ArrayList<Visit_Dim> visits = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int patientId = resultSet.getInt("id");
                String patientDOB = resultSet.getString("date_of_birth");
                int visitToken = resultSet.getInt("visit_token");
                String visitDate = resultSet.getString("visit_date");
                String diagnosis = resultSet.getString("description");
                double totalCost = resultSet.getDouble("total_cost");
                double procedureCost = resultSet.getDouble("procedure_cost");
                double medicationCost = resultSet.getDouble("medication_cost");

                visits.add(new Visit_Dim(visitToken, patientId, visitDate, totalCost, diagnosis, procedureCost, medicationCost, 1, patientDOB));
                lastLoaded[1] = visitToken;
            }
            return visits;

        } catch (Exception e) {
            System.err.println("Error loading visit data: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Med_dim> selectMedicineRecords() {
        String sql = "SELECT medicine_id, name, supplier_id FROM pharmacy_Inventory WHERE medicine_id > " + lastLoaded[2] + " ORDER BY medicine_id ASC";
        ArrayList<Med_dim> medicines = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int medId = resultSet.getInt("medicine_id");
                String medName = resultSet.getString("name");
                int supplierId = resultSet.getInt("supplier_id");

                medicines.add(new Med_dim(medId, medName, supplierId));
                lastLoaded[2] = medId;
            }
            return medicines;

        } catch (Exception e) {
            System.err.println("Error loading medicine records: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Supplier_Dim> selectSupplierRecords() {
        String sql = "SELECT * FROM Supplier WHERE supplier_id > " + lastLoaded[3] + " ORDER BY supplier_id ASC";
        ArrayList<Supplier_Dim> suppliers = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("supplier_id");
                String name = resultSet.getString("name");
                String contactInfo = resultSet.getString("contact_info");
                String address = resultSet.getString("address");

                suppliers.add(new Supplier_Dim(id, name, contactInfo, address));
                lastLoaded[3] = id;
            }
            return suppliers;

        } catch (Exception e) {
            System.err.println("Error loading supplier records: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<NumberOfVisit_Dim> selectNumberOfVisit() {
        String sql = "SELECT visit_date, COUNT(*) AS number_of_visits, MAX(visit_token) AS last_token " +
                "FROM visits WHERE visit_token > " + lastLoaded[4] +
                " GROUP BY visit_date ORDER BY last_token ASC";

        ArrayList<NumberOfVisit_Dim> visitsPerDay = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {
            while (resultSet.next()) {
                String visitDate = resultSet.getString("visit_date");
                int count = resultSet.getInt("number_of_visits");
                int lastToken = resultSet.getInt("last_token");
                visitsPerDay.add(new NumberOfVisit_Dim(visitDate, count));
                lastLoaded[4] = lastToken;
            }
            return visitsPerDay;
        } catch (Exception e) {
            System.err.println("Error loading visit records: " + e.getMessage());
            return null;
        }
    }

    public static ArrayList<Fact_Medicine> selectFact_Medicine() {
        String sql = """
        SELECT prescription.medicine_id, SUM(prescription.quantity) AS total_quantity,
               pharmacy_inventory.stock_level
        FROM prescription
        JOIN pharmacy_inventory ON prescription.medicine_id = pharmacy_inventory.medicine_id
        GROUP BY prescription.medicine_id, pharmacy_inventory.stock_level
        ORDER BY prescription.medicine_id ASC""";

        ArrayList<Fact_Medicine> factMedicines = new ArrayList<>();

        try (Connection connection = DriverManager.getConnection(url);
             PreparedStatement statement = connection.prepareStatement(sql);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int medicineId = resultSet.getInt("medicine_id");
                int totalQuantity = resultSet.getInt("total_quantity");
                int stockLevel = resultSet.getInt("stock_level");

                Fact_Medicine factMedicine = new Fact_Medicine(medicineId, totalQuantity, stockLevel);
                factMedicines.add(factMedicine);
            }
            return factMedicines;

        } catch (Exception e) {
            System.err.println("Error loading fact medicine records: " + e.getMessage());
            return null;
        }
    }
    public static void clearPreviousOperationalData() {
        String[] sqlStatements = {
                "DELETE FROM billing",
                "DELETE FROM visits",
                "DELETE FROM patient",
                "DELETE FROM prescription"
        };

        try (Connection connection = DriverManager.getConnection(url);
             Statement statement = connection.createStatement()) {

            // Auto-commit is true by default, so each executeUpdate commits immediately
            for (String sql : sqlStatements) {
                statement.executeUpdate(sql);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}


