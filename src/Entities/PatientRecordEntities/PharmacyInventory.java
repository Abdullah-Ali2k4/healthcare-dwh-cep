package Entities.PatientRecordEntities;

public class PharmacyInventory {
    String medName;
    int stockLevel;
    String dispenseData;

    public PharmacyInventory(String medName, int stockLevel, String dispenseData) {
        this.medName = medName;
        this.stockLevel = stockLevel;
        this.dispenseData = dispenseData;
    }

    public String getMedName() {
        return medName;
    }

    public int getStockLevel() {
        return stockLevel;
    }

    public String getDispenseData() {
        return dispenseData;
    }
}
