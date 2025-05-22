package Entities.DWH_Entity;

public class Supplier_Dim {
    int id;
    String name;
    String contact_info;
    String address;

    public Supplier_Dim(int id, String name, String contact_info, String address) {
        this.id = id;
        this.name = name;
        this.contact_info = contact_info;
        this.address = address;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getContact_info() {
        return contact_info;
    }

    public String getAddress() {
        return address;
    }
}
