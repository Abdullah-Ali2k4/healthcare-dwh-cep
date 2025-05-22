package DataTransformation;
import DatabaseControls.HospitalDWHControl;
import DatabaseControls.OperationalDBControl;
import Entities.DWH_Entity.*;
import Entities.PatientRecordEntities.Patient;

import java.util.ArrayList;

public class Load {
    public static void loadPatientDim(ArrayList<Patient> patients){
        HospitalDWHControl.insertDimPatientBatch(patients);
    }
    public static void loadVisitDim(ArrayList<Visit_Dim> visitDims){
        if (visitDims!=null)
            HospitalDWHControl.insertFactVisitBatch(visitDims);
    }
    public static void loadSupplier(ArrayList<Supplier_Dim> supplierDims){
        HospitalDWHControl.insertDimSupplierBatch(supplierDims);
    }
    public static void loadMedicineDim(){
        ArrayList<Med_dim> med_dims=OperationalDBControl.selectMedicineRecords();
        if (med_dims != null)
            HospitalDWHControl.insertDimMedicationBatch(med_dims);
    }
    public static void loadOutBreaks(ArrayList<Dim_Outbreak> dimOutbreaks){
        HospitalDWHControl.insertDimOutbreakBatch(dimOutbreaks);
    }
    public static void loadFactMedication(ArrayList<Fact_Medicine> factMedicines){
        HospitalDWHControl.insertFactMedicineBatch(factMedicines);
    }
    public static void loadNumberOfVisit(ArrayList<NumberOfVisit_Dim> factVisitPerMonths) {
        HospitalDWHControl.insertFactVisitPerMonthBatch(factVisitPerMonths);
    }
}
