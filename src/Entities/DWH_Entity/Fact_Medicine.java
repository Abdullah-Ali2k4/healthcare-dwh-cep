package Entities.DWH_Entity;

public class Fact_Medicine {
    int med_id;
    int current_stock;
    int used_stock;
    java.sql.Date last_updated;

    public Fact_Medicine(int med_id, int current_stock, int used_stock, java.sql.Date last_updated) {
        this.med_id = med_id;
        this.current_stock = current_stock;
        this.used_stock = used_stock;
        this.last_updated = last_updated;
    }

    public Fact_Medicine(int med_id, int current_stock, int used_stock) {
        this.med_id = med_id;
        this.current_stock = current_stock;
        this.used_stock = used_stock;
    }

    public int getMed_id() {
        return med_id;
    }

    public int getCurrent_stock() {
        return current_stock;
    }

    public int getUsed_stock() {
        return used_stock;
    }

    public java.sql.Date getLast_updated() {
        return last_updated;
    }
}
