package Entities;

public class Query_Entity {
     int medId;
     String medName;
     int usedStock;
     int currentStock;
     String lastUpdated;

    public Query_Entity(int medId, String medName, int usedStock, int currentStock, String lastUpdated) {
        this.medId = medId;
        this.medName = medName;
        this.usedStock = usedStock;
        this.currentStock = currentStock;
        this.lastUpdated = lastUpdated;
    }

    public int getMedId() {
        return medId;
    }

    public String getMedName() {
        return medName;
    }

    public int getUsedStock() {
        return usedStock;
    }

    public int getCurrentStock() {
        return currentStock;
    }

    public String getLastUpdated() {
        return lastUpdated;
    }
}
