package DataTransformation;

import Entities.DWH_Entity.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class FiltersHelperMethod {


    public static ArrayList<NumberOfVisit_Dim> aggregateMonths(ArrayList<NumberOfVisit_Dim> numberOfVisits) {
        Map<String, Integer> monthVisitMap = new HashMap<>();

        // Step 1: Aggregate counts into the map
        for (NumberOfVisit_Dim nOV : numberOfVisits) {
            String monthYear = separateYearMonth(nOV.getMonthYear());
            int currentCount = monthVisitMap.getOrDefault(monthYear, 0);
            monthVisitMap.put(monthYear, currentCount + nOV.getNumberOfVisits());
        }

        // Step 2: Convert map entries back to list
        ArrayList<NumberOfVisit_Dim> aggregatedList = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : monthVisitMap.entrySet()) {
            aggregatedList.add(new NumberOfVisit_Dim(entry.getKey(), entry.getValue()));
        }
        return aggregatedList;
    }

  public static String separateYearMonth(String date) {
        String d = checkDate(date);  // Assuming checkDate returns something like "12-March-2007"
        String[] dateParts = d.split("-");
        return dateParts[1] + "-" + dateParts[2];  // Returns [Month, Year]
    }

    public static double checkCost(double cost){
        double base_cost=1000;
        if(cost!=0){
            return cost;
        }
        else return base_cost;
    }

    public static int dateToAge(String dob, String visitDate) {
        // Define the date format (for "dd-MMMM-yyyy" format)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMMM-yyyy");
        // Convert String to LocalDate
        LocalDate dateOfBirth = LocalDate.parse(dob, formatter);
        LocalDate visitDateLocal = LocalDate.parse(visitDate, formatter);

        // Calculate the age using ChronoUnit
        int age = (int) ChronoUnit.YEARS.between(dateOfBirth, visitDateLocal);

        // Check if the birthday has occurred this year
        if (dateOfBirth.getMonthValue() > visitDateLocal.getMonthValue() ||
                (dateOfBirth.getMonthValue() == visitDateLocal.getMonthValue() &&
                        dateOfBirth.getDayOfMonth() > visitDateLocal.getDayOfMonth())) {
        }else age++;
        return age;
        }

    public static String checkName(String name){
        if (name==null){
            return "Anonymous";
        }
        return name;
    }

    public static String checkDate(String date) {
        // Check if the date is in the format day-year-month
        if (date.matches("\\d{1,2}-\\d{4}-\\w+")) {
            // Split the date string into day, year, and month
            String[] d = date.split("-");

            // Rearrange to the format: day-month-year
            return d[0] + "-" + d[2] + "-" + d[1];
        }
        // Return the original date if the format doesn't match
        return date;
    }

    public static LocalDate convertStringToDate(String dateString) {
        // Define the format that matches the input string
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MMMM-yyyy");

        // Parse the string into a LocalDate object
        return LocalDate.parse(dateString, formatter);
    }

    public static java.sql.Date convertStringToSqlDate(String dateString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-MMMM-yyyy");
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        return java.sql.Date.valueOf(localDate);
    }

    public static String getAgeGroup(String dob, String visitDate) {
        int age=dateToAge(dob,visitDate);
        if (age >= 1 && age <= 12) return "1-12";
        else if (age >= 13 && age <= 20) return "13-20";
        else if (age >= 21 && age <= 35) return "21-35";
        else if (age >= 36 && age <= 45) return "36-45";
        else if (age >= 46 && age <= 60) return "46-60";
        else if (age >= 60 && age <= 80) return "60-80";
        else if (age >= 80 && age <= 100) return "80-100";
        else if (age > 100) return "100+";
        else return "Unknown";  // In case of invalid age
    }
}
