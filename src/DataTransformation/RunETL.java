package DataTransformation;

import DatabaseControls.ExtractPublic_Health_Data;
import DatabaseControls.OperationalDBControl;

public class RunETL {
    public static String runETL(){
        Load.loadPatientDim(Transform.transformPatientData());
        Load.loadVisitDim(Transform.transformVisitDim());
        Load.loadSupplier(Transform.transformSupplier());
        Load.loadMedicineDim();
        Load.loadFactMedication(Transform.transformFactMedicine());
        Load.loadNumberOfVisit(Transform.transformNumberOfVisit());
        Load.loadOutBreaks(ExtractPublic_Health_Data.ExtractOutbreak());
        return "ETL COMPLETED";
    }
}
