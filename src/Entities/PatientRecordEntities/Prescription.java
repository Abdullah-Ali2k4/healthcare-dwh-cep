package Entities.PatientRecordEntities;

public class Prescription {
    private int medQuantity;

    public Prescription(int medQuantity) {
        this.medQuantity = medQuantity;
    }

    public int getMedQuantity() {
        return medQuantity;
    }
}
