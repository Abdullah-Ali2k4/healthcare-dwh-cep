package DataGenerators;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class generatePublicHealthData {

    static String filePath = "D:\\DWH-CEP\\src\\DataSources\\public_health_data.csv";  // Change as needed
    static int numRows = 50; // Adjust number of rows
    static Random random = new Random();
    public static void addPublicHealthData(){
        try (FileWriter writer = new FileWriter(filePath)) {
            writer.append("ZIP Code,Disease,monthYear,Reported Cases\n");

            String[] zipCodes = {"10001", "10002", "10003", "10004", "10005"};
            String[] diseases = {
                    "Cholera due to Vibrio cholerae 01 : biovar cholerae",
                    "Cholera due to Vibrio cholerae 01 : biovar eltor",
                    "Cholera : unspecified",
                    "Typhoid fever : unspecified",
                    "Typhoid meningitis",
                    "Typhoid fever with heart involvement",
                    "Typhoid pneumonia",
                    "Typhoid arthritis",
                    "Typhoid osteomyelitis",
                    "Typhoid fever with other complications",
                    "Paratyphoid fever A",
                    "Paratyphoid fever B",
                    "Paratyphoid fever C",
                    "Paratyphoid fever : unspecified",
                    "Salmonella enteritis",
                    "Salmonella sepsis",
                    "Localized salmonella infection : unspecified",
                    "Salmonella meningitis",
                    "Salmonella pneumonia",
                    "Salmonella arthritis",
                    "Salmonella osteomyelitis",
                    "Salmonella pyelonephritis",
                    "Salmonella with other localized infection",
                    "Other specified salmonella infections",
                    "Salmonella infection : unspecified",
                    "Shigellosis due to Shigella dysenteriae",
                    "Shigellosis due to Shigella flexneri"};


            for (int i = 0; i < numRows; i++) {
                String zipCode = zipCodes[random.nextInt(zipCodes.length)];
                String disease = diseases[random.nextInt(diseases.length)];
                int cases = random.nextInt(150,10000);
                String randomDate= randomDate(); // Random past date

                writer.append(zipCode).append(",");
                writer.append(disease).append(",");
                writer.append(randomDate).append(",");
                writer.append(String.valueOf(cases)).append("\n");
            }

            System.out.println("Dummy CSV file generated: " + filePath);
        } catch (IOException e) {
            System.err.println("Error writing CSV file: " + e.getMessage());
        }
    }

    static String randomDate(){
       String date= RandomData.date();
       String[] dates=date.split("-");
       return dates[1]+"-"+dates[2];
    }
}


