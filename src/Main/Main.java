package Main;

import DataGenerators.GenerateData;
import DataQueries.Queries;
import DataTransformation.RunETL;
import DatabaseControls.HospitalDWHControl;
import DatabaseControls.OperationalDBControl;

import java.util.Scanner;

public class Main {
    static Scanner input = new Scanner(System.in);

    public static void main(String[] args) {
        OperationalDBControl.clearPreviousOperationalData();
        HospitalDWHControl.clearAllDwhTables();
        while (true) {
            options();
        }
    }

    public static void generateData() {
        GenerateData.generateOneMonthData();
        RunETL.runETL();
    }

    public static void queries() {
        Queries queries = new Queries();
        System.out.println("\nQueries:");
        System.out.println("a. Average treatment cost per diagnosis by age group and gender");
        System.out.println("b. Medication stock levels and replenishment alerts based on patient demand trends");
        System.out.println("c. Correlation between local disease outbreaks and hospital visit spikes over time");
        System.out.print("Enter your choice: ");
        String choice = input.next();

        switch (choice) {
            case "a":
                queries.runQuery1();
                break;
            case "b":
                queries.query2Data();
                break;
            case "c":
                queries.runQuery3();
                break;
            default:
                System.out.println("Wrong input.");
        }
    }

    public static void options() {
        System.out.println("\nOptions:");
        System.out.println("c. Generate Data");
        System.out.println("q. Run Query");
        System.out.print("Enter your choice: ");
        String s = input.next();

        switch (s) {
            case "c":
                generateData();
                break;
            case "q":
                queries();
                break;
            default:
                System.out.println("Invalid choice.");
        }
    }
}
