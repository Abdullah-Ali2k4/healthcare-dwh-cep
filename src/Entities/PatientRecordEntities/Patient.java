package Entities.PatientRecordEntities;

import java.sql.Date;

public class Patient {
    int id;
    String name;
    String dateOfBirth;
    String gender;
    java.sql.Date DOB;

    public java.sql.Date getDOB() {
        return DOB;
    }

    public void setDOB(java.sql.Date DOB) {
        this.DOB = DOB;
    }

    public Patient( int id, String name,Date DOB, String gender) {
        this.DOB = DOB;
        this.gender = gender;
        this.id = id;
        this.name = name;
    }

    public Patient(int id, String name, String dateOfBirth, String gender) {
        this.id = id;
        this.name = name;
        this.dateOfBirth = dateOfBirth;
        this.gender = gender;
    }

    public Patient(String name, String dateOfBirth, String gender) {

        this.name = name;
        this.dateOfBirth=dateOfBirth;
        this.gender = gender;
    }


    public String getName() {
        return name;
    }



    public String getGender() {
        return gender;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public int getId() {
        return id;
    }
}
