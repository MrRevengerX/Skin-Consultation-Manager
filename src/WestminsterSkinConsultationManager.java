import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager {

    public static DoctorList doctorList = new DoctorList();

    static GuiSkinConsultationHome gui = new GuiSkinConsultationHome();
    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);

        WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();


        //Check whether the log file is already there then retrieve data from it. If it isn't create empty log file.
        manager.loadSavedDetails();

            while (true) {
            // Print the menu
            System.out.println("\nMenu");
            System.out.println("---------------------------------");
            System.out.println("1. Add a doctor");
            System.out.println("2. Remove a doctor");
            System.out.println("3. Print doctors");
            System.out.println("4. Save data");
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
                    manager.addDoctor();
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
                    // Code for option 5
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

            System.out.println("\nEnter doctor details\n");

            // get name from user
            System.out.print("Enter firstname: ");
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
                    if (doctorList.getDoctorList().containsKey(medicalLicenseNumber)){
                        System.out.println("Medical license number should be unique.");
                    }else {
                        medicalLicenseNumberValid = true;
                    }

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
                    System.out.println("\nSelected Doctor's Details:");
                    System.out.println("---------------------");
                    System.out.println("Medical License : " + doctor.getmLicense());
                    System.out.println("Name: " + doctor.getName());
                    System.out.println("Surname: " + doctor.getSurname());
                    System.out.println("Date of Birth: " + doctor.getDob());
                    System.out.println("Mobile Number: " + doctor.getMobileNum());
                    System.out.println("NIC: " + doctor.getNic());
                    System.out.println("Specialization: " + doctor.getSpeciali());
                    System.out.print("\nARE YOU SURE YOU WANT TO DELETE THIS DOCTOR? (Y/N) ");
                    String confirm = scanner.next();
                    if (confirm.equalsIgnoreCase("Y")) {
                        doctorList.removeDoctor(medicalLicenseNumber);
                        System.out.println("Doctor has been removed.\n");
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
            System.out.println("Data saved.\n");
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
                System.out.println("Data overwritten.\n");
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
                System.out.println("Saved data has been loaded.");
            } catch (IOException e) {
                System.out.println("No saved data has been found.");
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }

}