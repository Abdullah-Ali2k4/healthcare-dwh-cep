package DataGenerators;

public class GenerateData{
    private static String generateOneDayData(){
        for (int i = 0; i < 10; i++) {GenerateHospitalData.addRandomPatientData();}
        for (int i = 0; i < 5; i++) {GenerateHospitalData.addRandomVisitData();}
        for (int i = 0; i < 5; i++) {GenerateHospitalData.addRandomBillingData();}
        for (int i = 0; i < 5; i++) {GenerateHospitalData.addPrescription();}
        return "A Day Data loaded";
    }
    public static String generateOneMonthData(){
        RandomData.start();
        System.out.println(RandomData.date());
        for (int i = 0; i < 27; i++) {
            generateOneDayData();
            RandomData.updateDate();
        }
        generatePublicHealthData.addPublicHealthData();
        generateOneDayData();
        RandomData.updateDate();
        return "A month Data loaded";
    }
}

