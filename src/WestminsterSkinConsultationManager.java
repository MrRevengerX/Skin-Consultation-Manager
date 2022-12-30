import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {


    public static DoctorList doctorList = new DoctorList();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();
        GuiSkinConsultationHome gui = new GuiSkinConsultationHome();

        doctorList.addDoctor(12345, new Doctor("John", "Doe", LocalDate.of(1980, 1, 1), 123456789, "1234567890123", 12345, "Dermatology"));
        doctorList.addDoctor(23456, new Doctor("Jane", "Doe", LocalDate.of(1985, 5, 5), 234567890, "2345678901234", 23456, "Cardiology"));
        doctorList.addDoctor(34567, new Doctor("Bob", "Smith", LocalDate.of(1970, 10, 10), 345678901, "3456789012345", 34567, "Pediatrics"));
        doctorList.addDoctor(125145, new Doctor("John", "Doe", LocalDate.of(1980, 1, 1), 123456789, "1234567890123", 125145, "Dermatology"));
        doctorList.addDoctor(23424, new Doctor("Jane", "Doe", LocalDate.of(1985, 5, 5), 234567890, "2345678901234", 23424, "Cardiology"));
        doctorList.addDoctor(34512, new Doctor("Bob", "Smith", LocalDate.of(1970, 10, 10), 345678901, "3456789012345", 34512, "Pediatrics"));
        doctorList.addDoctor(12635, new Doctor("John", "Doe", LocalDate.of(1980, 1, 1), 123456789, "1234567890123", 12635, "Dermatology"));
        doctorList.addDoctor(234616, new Doctor("Jane", "Doe", LocalDate.of(1985, 5, 5), 234567890, "2345678901234", 234616, "Cardiology"));
        doctorList.addDoctor(3456723, new Doctor("Bob", "Araliya", LocalDate.of(1970, 10, 10), 345678901, "3456789012345", 3456723, "Pediatrics"));
        doctorList.addDoctor(1234566, new Doctor("John", "Doe", LocalDate.of(1980, 1, 1), 123456789, "1234567890123", 1234566, "Dermatology"));

        //Check whether the log file is already there and if it isn't create empty log file.

        manager.loadSavedDetails();

            while (true) {
            // Print the menu
            System.out.println("\nMenu");
            System.out.println("---------------------------------");
            System.out.println("1. Add a doctor");
            System.out.println("2. Remove a doctor");
            System.out.println("3. Print doctors");
            System.out.println("4. Save doctors");
            System.out.println("5. Open GUI");
            System.out.println("6. Quit\n");

            // need sum of two numbers

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
                    manager.addDoctor();
                    System.out.println(doctorList.toString());
                    break;
                case 2:
                    // Code for option 2
                    manager.removeDoctor();
                    break;
                case 3:
                    // Code for option 3
                    manager.printDoctors();
                    break;
                case 4:
                    // Code for option 4
                    manager.saveData();
                    break;
                case 5:
                    // Code for option 4
                    gui.main(args);
                    break;
                case 6:
                    // Code for quitting the program
                    return;
                default:
                    // Code for invalid input
                    System.out.println("Invalid choice. Please try again.\n");
                    break;
            }
        }
    }

    public DoctorList getDoctorList() {
        return doctorList;
    }
    @Override
    public void addDoctor() {
        Scanner scanner = new Scanner(System.in);
        if (doctorList.getDoctorList().size() != 10){
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
        }else{
            System.out.println("Doctor list is full");
        }



    }

    @Override
    public void removeDoctor() {
        Scanner scanner = new Scanner(System.in);

        try {
            if (doctorList.getDoctorList().isEmpty()) {
                System.out.println("Doctor list is empty.");
            } else {
                System.out.println("Doctor List");
                System.out.println("------------");
                System.out.printf("%-20s %-20s %-15s %-20s %-20s\n", "Medical License", "Name", "Surname", "Mobile Number", "Specialization");
                for (Doctor doctor : doctorList.getDoctorList().values()) {
                    System.out.printf("%-20d %-20s %-15s %-20d %-20s\n", doctor.getmLicense(), doctor.getName(), doctor.getSurname(), doctor.getMobileNum(), doctor.getSpeciali());
                }
                System.out.print("\nEnter medical license number of the doctor to remove: ");
                int medicalLicenseNumber = scanner.nextInt();
                Doctor doctor = doctorList.getDoctorList().get(medicalLicenseNumber);
                if (doctor != null) {
                    System.out.println("\nDoctor Details:");
                    System.out.println("---------------------");
                    System.out.println("Medical License :\t" + doctor.getmLicense());
                    System.out.println("Name:\t" + doctor.getName());
                    System.out.println("Surname:\t" + doctor.getSurname());
                    System.out.println("Date of Birth:\t" + doctor.getDob());
                    System.out.println("Mobile Number:\t" + doctor.getMobileNum());
                    System.out.println("NIC:\t" + doctor.getNic());
                    System.out.println("Specialization:\t" + doctor.getSpeciali());
                    System.out.print("ARE YOU SURE YOU WANT TO DELETE THIS DOCTOR? (Y/N) ");
                    String confirm = scanner.next();
                    if (confirm.equalsIgnoreCase("Y")) {
                        doctorList.getDoctorList().remove(medicalLicenseNumber);
                    }
                } else {
                    System.out.println("Doctor with medical license number " + medicalLicenseNumber + " not found.");
                }
            }
        } catch (InputMismatchException e) {
            System.out.println("Invalid medical license number. Please enter a valid integer.");
            scanner.nextLine(); // consume the invalid input
        }
    }

    @Override
    public void printDoctors() {
        if (doctorList.getDoctorList().isEmpty()) {
            System.out.println("Doctor list is empty.");
        } else {
            ArrayList<Doctor> doctorArrayList = new ArrayList<>(doctorList.getDoctorList().values());

            Collections.sort(doctorArrayList, new Comparator<Doctor>() {
                @Override
                public int compare(Doctor d1, Doctor d2) {
                    return d1.getSurname().compareTo(d2.getSurname());
                }
            });

            System.out.println("Doctor List");
            System.out.println("------------");
            System.out.printf("%-20s %-20s %-12s %-15s %-15s %-20s %-20s\n", "Name", "Surname", "DOB", "Mobile Number", "NIC", "Medical License", "Specialization");
            for (Doctor doctor : doctorArrayList) {
                System.out.printf("%-20s %-20s %-12s %-15d %-15s %-20d %-20s\n", doctor.getName(), doctor.getSurname(), doctor.getDob(), doctor.getMobileNum(), doctor.getNic(), doctor.getmLicense(), doctor.getSpeciali());
            }
        }
    }

    @Override
    public void saveData() {
        Scanner scanner = new Scanner(System.in);

        File file = new File("log.txt");
        if (file.length() == 0) {
            // Save the doctorList HashMap to the file
        try {
            FileOutputStream fos = new FileOutputStream(file);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(doctorList.getDoctorList());
            oos.close();
            fos.close();
        } catch (IOException e) {
            System.out.println("Error saving doctor list: " + e.getMessage());
        }
    } else {
        // Ask the user if they want to replace the data in the file
        System.out.print("File already contains data. Do you want to replace it? (Y/N) ");
        String response = scanner.nextLine();
        if (response.equalsIgnoreCase("Y")) {
            // Save the doctorList HashMap to the file
            try {
                FileOutputStream fos = new FileOutputStream(file);
                ObjectOutputStream oos = new ObjectOutputStream(fos);
                oos.writeObject(doctorList.getDoctorList());
                oos.close();
                fos.close();
            } catch (IOException e) {
                System.out.println("Error saving doctor list: " + e.getMessage());
            }
        }
        }
    }

    @Override
    public void loadSavedDetails(){
        File file = new File("log.txt");

        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.println("Error creating file: " + e.getMessage());
            }
        }else {
            // Read the doctorList HashMap from the file
            try {
                FileInputStream fis = new FileInputStream(file);
                ObjectInputStream ois = new ObjectInputStream(fis);
                doctorList.setDoctorList((HashMap<Integer, Doctor>) ois.readObject());
                ois.close();
                fis.close();
            } catch (IOException e) {
                System.out.println("Error reading doctor list: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}