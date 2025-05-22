package Entities.DWH_Entity;



import java.sql.Date;
import java.math.BigDecimal;

public class Visit_Dim {
    private int visitId;
    private int patientId;
    private Date visitDate;
    private double totalCost;
    private String diagnosisName;
    private int hospitalId;
    private String DOB;
    private String ageGroup;
    private double procedureCost;
    private double medicationCost;
    private String visitDate1;

    // Constructor
    public Visit_Dim(int visitId, int patientId, Date visitDate, double totalCost,
                     String diagnosisName, int hospitalId, String ageGroup) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.visitDate = visitDate;
        this.totalCost = totalCost;
        this.diagnosisName = diagnosisName;
        this.hospitalId = hospitalId;
        this.ageGroup = ageGroup;
    }


    public Visit_Dim(int visitId, int patientId, String visitDate, double totalCost, String diagnosisName, double procedureCost, double medicationCost, int hospitalId, String DOB) {
        this.visitId = visitId;
        this.patientId = patientId;
        this.visitDate1 = visitDate;
        this.totalCost = totalCost;
        this.diagnosisName = diagnosisName;
        this.medicationCost = medicationCost;
        this.procedureCost = procedureCost;
        this.hospitalId = hospitalId;
        this.DOB = DOB;
    }

    // Getters and Setters
    public int getVisitId() {
        return visitId;
    }


    public int getPatientId() {
        return patientId;
    }

    public Date getVisitDate() {
        return visitDate;
    }


    public double getTotalCost() {
        return totalCost;
    }


    public String getDiagnosisName() {
        return diagnosisName;
    }

    public int getHospitalId() {
        return hospitalId;
    }

    public double getProcedureCost() {
        return procedureCost;
    }

    public double getMedicationCost() {
        return medicationCost;
    }


    public String getAgeGroup() {
        return ageGroup;
    }

    public String getVisitDate1() {
        return visitDate1;
    }

    public String getDOB() {
        return DOB;
    }
}
