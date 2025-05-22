package DataGenerators;

import DatabaseControls.OperationalDBControl;
import Entities.PatientRecordEntities.*;


public class GenerateHospitalData {
    static void addRandomPatientData(){
        String gender= RandomData.randomGender();
        String name=RandomData.randomName(gender);
        OperationalDBControl.addPatientRecord(new Patient(name,RandomData.randomDOB(),gender));
    }
    static void addRandomVisitData(){
        OperationalDBControl.addVisitRecord(new Visit(RandomData.randomDate()));
    }
    public static void addPrescription(){
        OperationalDBControl.addPrescriptionRecord(new Prescription(RandomData.randomQuantity()));
    }
    public static void addRandomBillingData(){
        OperationalDBControl.addBillingRecord(new Billing(RandomData.randomCost(),RandomData.randomCost(),
                RandomData.randomInsuranceStatus()));
    }

}
