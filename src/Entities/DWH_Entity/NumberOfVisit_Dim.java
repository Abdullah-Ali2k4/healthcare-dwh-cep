package Entities.DWH_Entity;

public class NumberOfVisit_Dim {

    int numberOfVisits=0;
    String monthYear;


    public NumberOfVisit_Dim(String monthYear, int numberOfVisits) {
        this.monthYear = monthYear;
        this.numberOfVisits = numberOfVisits;
    }



    public int getNumberOfVisits() {
        return numberOfVisits;
    }


    public String getMonthYear() {
        return monthYear;
    }


}
