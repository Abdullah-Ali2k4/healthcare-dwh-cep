package Entities.PatientRecordEntities;

public class Billing {
    private int procedure_cost;
    private int medication_cost;
    private String insurance_claim_status;

    public Billing(int procedure_cost, int medication_cost, String insurance_claim_status) {
        this.procedure_cost = procedure_cost;
        this.medication_cost = medication_cost;
        this.insurance_claim_status = insurance_claim_status;
    }

    public int getProcedure_cost() {
        return procedure_cost;
    }

    public int getMedication_cost() {
        return medication_cost;
    }

    public String getInsurance_claim_status() {
        return insurance_claim_status;
    }
}
