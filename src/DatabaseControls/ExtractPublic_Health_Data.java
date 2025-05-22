package DatabaseControls;
import Entities.DWH_Entity.Dim_Outbreak;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class ExtractPublic_Health_Data {


        public static ArrayList<Dim_Outbreak> ExtractOutbreak() {
            String filePath = "src/DataSources/public_health_data.csv";
            String line = "";
            String delimiter = ",";
            ArrayList<Dim_Outbreak> dimOutbreaks=new ArrayList<>();
            try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
                br.readLine();
                while ((line = br.readLine()) != null) {
                    String[] values = line.split(delimiter);
                    int numberOfOutbreak = Integer.parseInt(values[3].trim());
                    dimOutbreaks.add(new Dim_Outbreak(values[1], values[0], numberOfOutbreak, values[2]));
                }
                return dimOutbreaks;

            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
    }

