package DataQueries;

import Entities.Query_Entity;

import javax.management.Query;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashSet;

public class Queries {

    private static final String URL = "jdbc:postgresql://localhost:5432/dwh_hospital";  // Database URL
    private static final String USER = "postgres";  // Database username
    private static final String PASSWORD = "secure#123";  // Database password

    // Method to establish a connection to the database
    private static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    // First query
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
                double avgCost = rs.getDouble("avg_cost");  // Using the alias "avg_cost"
                String ageGroup = rs.getString("age_group");
                String diagnosisName = rs.getString("diagnosis_name");

                System.out.println("Diagnosis: " + diagnosisName+"\nGender: " + gender + "\nAge Group: " + ageGroup +",\nAverage Cost: " + avgCost+"\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


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
                        + "\nOutbreaks: " + numberOfOutbreak+"\n\n");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

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
        ArrayList<Query_Entity> queries=new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                int medId = rs.getInt("med_id");
                String medName = rs.getString("med_name");
                int usedStock = rs.getInt("used_stock");
                int currentStock = rs.getInt("current_stock");
                String lastUpdated = rs.getString("last_updated");

                queries.add(new Query_Entity(medId,medName,usedStock,currentStock,lastUpdated));

            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return queries;
    }

    public ArrayList<ArrayList<Query_Entity>> query2Data() {
        ArrayList<Query_Entity> arrayList = runQuery2();
        ArrayList<ArrayList<Query_Entity>> query = new ArrayList<>();
        HashSet<Integer> visitedMedIds = new HashSet<>();

        for (Query_Entity entity : arrayList) {
            int medId = entity.getMedId();

            if (visitedMedIds.contains(medId)) continue; // Already grouped this medId

            ArrayList<Query_Entity> group = new ArrayList<>();

            for (Query_Entity check : arrayList) {
                if (check.getMedId() == medId) {
                    group.add(check);
                }
            }

            // Only add if more than one entry with same medId but different lastUpdated
            HashSet<String> uniqueDates = new HashSet<>();
            for (Query_Entity e : group) {
                uniqueDates.add(e.getLastUpdated());
            }

            if (uniqueDates.size() > 1) {
                query.add(group);
            }

            visitedMedIds.add(medId);
        }

        // Optional: Print result
        for (ArrayList<Query_Entity> group : query) {
            System.out.println("Group:\n");
            for (Query_Entity q : group) {
                System.out.println("MedID: " + q.getMedId() +"\nMedicine Name: "+q.getMedName()+"\nUsed Stock: " +q.getUsedStock()+"\nCurrent Stock: "+q.getCurrentStock()+"\nLastUpdated: " + q.getLastUpdated()+"\n");
            }
            System.out.println("\n-----------\n");
        }
        return query;
    }
}
