package DataTransformation;

import DataGenerators.RandomData;
import DatabaseControls.OperationalDBControl;
import Entities.DWH_Entity.Fact_Medicine;
import Entities.DWH_Entity.NumberOfVisit_Dim;
import Entities.DWH_Entity.Supplier_Dim;
import Entities.DWH_Entity.Visit_Dim;
import Entities.PatientRecordEntities.Patient;

import java.util.ArrayList;

import static DataTransformation.FiltersHelperMethod.*;

public class Transform {
    public static ArrayList<Patient> transformPatientData(){
        ArrayList<Patient> patients= OperationalDBControl.selectPatientRecords();
        ArrayList<Patient> transformedPatients=new ArrayList<>();
        assert patients != null;
        for (Patient p:patients){
            p.setDOB(convertStringToSqlDate(checkDate(p.getDateOfBirth())));
            transformedPatients.add(new Patient(p.getId(),checkName(p.getName()),p.getDOB(),p.getGender()));
        }
        return transformedPatients;
    }
    public static ArrayList<Visit_Dim> transformVisitDim(){
        ArrayList<Visit_Dim> visitDim = OperationalDBControl.selectVisitsAndBilling();
        ArrayList<Visit_Dim> transformedVisits=new ArrayList<>();
        for (Visit_Dim vd :visitDim ) {
            int patientId=vd.getPatientId();
            int visitID=vd.getVisitId();
            String diagnosisName=vd.getDiagnosisName();
            int hospitalId=vd.getHospitalId();
            double totalCost=checkCost(vd.getProcedureCost())+checkCost(vd.getMedicationCost());
            String DOB=checkDate(vd.getDOB());
            String visitDate=checkDate(vd.getVisitDate1());
            String ageGroup=getAgeGroup(DOB, visitDate);
            transformedVisits.add(new Visit_Dim(visitID,patientId,convertStringToSqlDate(visitDate),totalCost,diagnosisName,hospitalId,ageGroup));
        }
        return transformedVisits;
    }
    public static ArrayList<Supplier_Dim> transformSupplier(){
        ArrayList<Supplier_Dim> supplierDim = OperationalDBControl.selectSupplierRecords();
        ArrayList<Supplier_Dim> transformedSupplier = new ArrayList<>();
        for (Supplier_Dim supplier_dim:supplierDim) {
            String name=checkName(supplier_dim.getName());
            transformedSupplier.add(new Supplier_Dim(supplier_dim.getId(),name,supplier_dim.getContact_info(),supplier_dim.getAddress()));
        }
        return transformedSupplier;
    }
    public static ArrayList<NumberOfVisit_Dim> transformNumberOfVisit(){
        ArrayList<NumberOfVisit_Dim> dim_numberOfVisit = aggregateMonths(OperationalDBControl.selectNumberOfVisit());
        return dim_numberOfVisit;
    }
    public static ArrayList<Fact_Medicine> transformFactMedicine(){
        ArrayList<Fact_Medicine>fact_medicines=OperationalDBControl.selectFact_Medicine();
        ArrayList<Fact_Medicine> transformFactMedicines=new ArrayList<>();
        for (Fact_Medicine factMedicine:fact_medicines) {
            transformFactMedicines.add(new Fact_Medicine(factMedicine.getMed_id(),factMedicine.getCurrent_stock(),factMedicine.getUsed_stock(), convertStringToSqlDate(RandomData.date())));
        }
        return transformFactMedicines;
    }
}
