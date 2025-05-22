package Entities.DWH_Entity;

public class Med_dim {
   int med_id;
    String med_name;
    int supplier_id;

    public Med_dim(int med_id, String med_name, int supplier_id) {
        this.med_id = med_id;
        this.med_name = med_name;
        this.supplier_id = supplier_id;
    }

    public int getMed_id() {
        return med_id;
    }

    public String getMed_name() {
        return med_name;
    }

    public int getSupplier_id() {
        return supplier_id;
    }
}
