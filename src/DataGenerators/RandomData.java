package DataGenerators;

import java.io.*;
import java.time.LocalDate;
import java.util.Random;

public class RandomData {
    static String[] months = {
            "January", "February", "March", "April", "May", "June",
            "July", "August", "September", "October", "November", "December"
    };

        private static final String[] streets = {"Iqbal Road", "Jinnah Avenue", "Faisal Street", "Gulberg Main Road", "Shahrah-e-Faisal", "University Road", "Mall Road", "GT Road", "M.A. Jinnah Road", "Zamzama Boulevard"};
        private static final String[] cities = {"Karachi--Sindh", "Lahore--Punjab", "Islamabad--FATA", "Rawalpindi--Punjab", "Faisalabad--Punjab", "Peshawar--KPK", "Quetta--Balochistan", "Multan--Punjab", "Hyderabad--Sindh", "Sialkot--Punjab"};


        // Generates a random mobile number
        public static String randomContact() {
            String[] mobilePrefixes = {"030", "031", "032", "033", "034", "035"};
            return mobilePrefixes[random.nextInt(mobilePrefixes.length)] + (10000000 + random.nextInt(9000000));
        }

        // Generates a random address
        public static String randomAddress() {
            int p=random.nextInt(0,100);
            if (p<10)
                return null;
            int houseNumber = random.nextInt(200) + 1;  // Random house number (1-200)
            String street = streets[random.nextInt(streets.length)];
            String city = cities[random.nextInt(cities.length)];

            return "House #" + houseNumber + ", " + street + ", " +city+ ", Pakistan";
        }



    static String[][] names = new String[2][700];
    static void readName(String g) throws IOException {
        int k = g.equalsIgnoreCase("male") ? 0 : 1;

        BufferedReader reader = new BufferedReader(new FileReader("src/DataGenerators/" + g.toLowerCase() + "_names.csv"));
        String line;
        int i = 0;
        while ((line = reader.readLine()) != null && i < 700) {  // Prevent out-of-bounds error
            names[k][i] = line.trim();  // Trim to remove unwanted spaces
            i++;
        }
        reader.close();
    }
    public static void start(){
        try {
            readName("male");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        try {
            readName("female");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    static public String randomDOB(){
        LocalDate currentDate = LocalDate.now();

        return random.nextInt(1,28)+"-"+months[random.nextInt(11)]+"-"+(year-randomAge());
    }
    static private Random random =new Random();


    static String randomName(String gender){
        int p = random.nextInt(100); // Generates a number between 0-99
        if (p < 10){
            return null;
        }

        if (gender.equalsIgnoreCase("male"))
            return names[0][random.nextInt(500)]+" "+names[0][random.nextInt(500)];
        else return names[1][random.nextInt(500)]+" "+names[0][random.nextInt(500)];
    }


    static int date=1;
    static int month=0;
    static int year =2007;
    static void updateDate(){
        if(date!=28) date++;
        else if(month!=11) {month++; date=1;}
        else {year++;month=0;date=1;}
    }
    static String randomDate() {
        int p = random.nextInt(100); // Generates a number between 0-99

        if (p > 10) {
            // 90% chance: "DD-MMM-YYYY" format
            return date + "-" + months[month] + "-" + year;
        } else {
            // 10% chance: "DD-YYYY-MMM" format (inconsistent case)
            return date + "-" + year + "-" + months[month];
        }
    }

    static int randomAge(){
        return random.nextInt(110);
    }
    static String randomGender(){
        String[] gender={"Male", "Female", "Other"};
        return gender[random.nextInt(2)];
    }
    static String randomInsuranceStatus(){
        String[] insuranceStatus={"Pending", "Approved", "Rejected"};
        return  insuranceStatus[random.nextInt(2)];
    }
    static int randomCost(){
        int p = random.nextInt(100);
        if (p>10)
            return random.nextInt(200,2000);
        return 0;
    }
    static int randomQuantity(){
        return random.nextInt(3,30);
    }
    public static String date(){
        return date+"-"+months[month]+"-"+year;
    }
}
