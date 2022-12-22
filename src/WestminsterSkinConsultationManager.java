import javax.print.Doc;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {


    public static DoctorList doctorList = new DoctorList();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        WestminsterSkinConsultationManager system = new WestminsterSkinConsultationManager();

        while (true) {
            // Print the menu
            System.out.println("\nMenu:");
            System.out.println("1. Add a doctor");
            System.out.println("2. Option 2");
            System.out.println("3. Option 3");
            System.out.println("4. Quit\n");

            int choice = 0;
            // Read the user's input
            try {
                System.out.print("Enter your choice : ");
                choice = input.nextInt();
            } catch (InputMismatchException e) {
                // If the user entered a string instead of an integer, display an error message
                System.out.println("Invalid input. Please enter a number.\n");
                input.nextLine();
                continue;
            }


            // Use a switch statement to execute the appropriate code for the user's choice
            switch (choice) {
                case 1:
                    System.out.println("1 selected");
                    system.addDoctor();
                    System.out.println(doctorList.toString());
                    break;
                case 2:
                    // Code for option 2
                    break;
                case 3:
                    // Code for option 3
                    break;
                case 4:
                    // Code for quitting the program
                    return;
                default:
                    // Code for invalid input
                    System.out.println("Invalid choice. Please try again.\n");
                    break;
            }
        }
    }


    @Override
    public void addDoctor() {
        Scanner scanner = new Scanner(System.in);

        // get name from user
        System.out.print("Enter name: ");
        String name = scanner.nextLine();

        // get surname from user
        System.out.print("Enter surname: ");
        String surname = scanner.nextLine();

        // get date of birth from user
        LocalDate dob = null;
        boolean dobValid = false;
        while (!dobValid) {
            System.out.print("Enter date of birth (YYYY-MM-DD): ");
            try {
                dob = LocalDate.parse(scanner.nextLine());
                dobValid = true;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please enter the date in the format YYYY-MM-DD.");
            }
        }

        // get mobile number from user
        int mobileNum = 0;
        boolean mobileNumValid = false;
        while (!mobileNumValid) {
            System.out.print("Enter mobile number: ");
            try {
                mobileNum = scanner.nextInt();
                mobileNumValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid mobile number. Please enter a valid integer.");
                scanner.nextLine(); // consume the invalid input
            }
        }

        // get NIC from user
        scanner.nextLine(); // consume the newline character
        System.out.print("Enter NIC: ");
        String nic = scanner.nextLine();

        // get medical license number from user
        int medicalLicenseNumber = 0;
        boolean medicalLicenseNumberValid = false;
        while (!medicalLicenseNumberValid) {
            System.out.print("Enter medical license number: ");
            try {
                medicalLicenseNumber = scanner.nextInt();
                medicalLicenseNumberValid = true;
            } catch (InputMismatchException e) {
                System.out.println("Invalid medical license number. Please enter a valid integer.");
                scanner.nextLine(); // consume the invalid input
            }
        }

        // get specialization from user
        scanner.nextLine();
        System.out.print("Enter specialization: ");
        String specialization = scanner.nextLine();
        // create a new Doctor object with the user-provided information
        Doctor doctor = new Doctor(name, surname, dob, mobileNum, nic, medicalLicenseNumber, specialization);
        doctorList.addDoctor(medicalLicenseNumber,doctor);
    }
}