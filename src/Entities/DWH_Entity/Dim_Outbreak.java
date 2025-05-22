package Entities.DWH_Entity;

public class Dim_Outbreak {
//    private int outbreakId;
    private String diseaseName;

    private String zip;
    private int numberOfOutbreak;
    private String outbreakMonthYear;

    // Constructor
    public Dim_Outbreak(String diseaseName, String zip, int numberOfOutbreak, String outbreakMonthYear) {
        this.diseaseName = diseaseName;

        this.zip = zip;
        this.numberOfOutbreak = numberOfOutbreak;
        this.outbreakMonthYear = outbreakMonthYear;
    }


    public String getDiseaseName() {
        return diseaseName;
    }


    public String getZip() {
        return zip;
    }

    public int getNumberOfOutbreak() {
        return numberOfOutbreak;
    }

    public String getOutbreakMonthYear() {
        return outbreakMonthYear;
    }
}

