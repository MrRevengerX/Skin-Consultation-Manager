import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GuiSkinConsultationHome {

    static JFrame homeFrame;
    static JFrame doctorListFrame;

    static JFrame selectDoctorFrame;

    static MyButton doctorListButton;

    static MyButton bookConsultationButton;

    static MyButton viewConsultationsButton;

    static WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

    static HashMap<String, Patient> pastPatients = new HashMap<>();

    static HashMap<String, Consultation> consultationList = new HashMap<>();

    static int count;


    public static void main(String[] args) {

        //Restore data from file
        for (Doctor doctor : manager.getDoctorList().getDoctorList().values()) {
            for (Consultation consultation : doctor.getConsultation()) {
                consultationList.put(consultation.getConsultationID(), consultation);
                pastPatients.put(consultation.getPatient().getPatientID(), consultation.getPatient());
            }
        }
        count = consultationList.size();

        homeScreen();

    }

    public static void homeScreen(){
        homeFrame = new JFrame("Skin Consultation Home");

        homeFrame.setSize(1366, 768);
        homeFrame.setLocationRelativeTo(null);
        homeFrame.setResizable(false);
        homeFrame.setLayout(null);

        // Adding a background image
        JLabel background = new JLabel(new ImageIcon("background.png"));
        background.setBounds(0, 0, 1366, 768);


        JLabel consultationName = new JLabel("Westminster Skin");
        JLabel consultationName2 = new JLabel("Consultation");

        consultationName.setBounds(800, 120, 1366, 100);
        consultationName.setFont(new Font("Montserrat", Font.BOLD, 50));
        consultationName.setForeground(new Color(0x7BB7C8));

        consultationName2.setBounds(860, 190, 1366, 100);
        consultationName2.setFont(new Font("Montserrat", Font.BOLD, 50));
        consultationName2.setForeground(new Color(0x7BB7C8));


        //Creating buttons for home window
        doctorListButton = new MyButton("Doctor List");
        bookConsultationButton = new MyButton("Book Consultation");
        viewConsultationsButton = new MyButton("View Consultations");


        doctorListButton.setBounds(925, 310, 230, 50);
        bookConsultationButton.setBounds(925, 410, 230, 50);
        viewConsultationsButton.setBounds(925, 510, 230, 50);

        // Adding buttons to the frame
        homeFrame.add(doctorListButton);
        homeFrame.add(consultationName);
        homeFrame.add(consultationName2);
        homeFrame.add(bookConsultationButton);
        homeFrame.add(viewConsultationsButton);
        homeFrame.add(background);
        homeFrame.setVisible(true);

        //Adding action listener to the buttons
        doctorListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create the doctor list window
                showDoctorsList();
                doctorListButton.setEnabled(false);
            }
        });

        bookConsultationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                bookConsultation();
                bookConsultationButton.setEnabled(false);
            }
        });

        viewConsultationsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(consultationList.size() == 0) {
                    JOptionPane.showMessageDialog(homeFrame, "No consultations have been booked yet!");
                } else {
                    viewConsultations();
                    viewConsultationsButton.setEnabled(false);
                }
            }
        });
    }

    // Creating a custom button template
    public static class MyButton extends JButton {

        public MyButton(String text) {
            super(text);

            // Setting the font, size, and color for button text
            this.setFont(new Font("Montserrat", Font.BOLD, 18));
            this.setForeground(Color.WHITE);

            this.setBackground(new Color(0x7BB7C8));
            this.setSize(150, 50);


            this.setFocusPainted(false);


        }
    }

    public static void showDoctorsList() {

        String[] columnNames = {"Medical License", "Name", "Surname", "Mobile Number", "Specialization"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);

        DoctorList doctorList = manager.getDoctorList();

        // Iterating through the doctorList HashMap and add each Doctor object to rows
        for (HashMap.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
            Doctor doctor = entry.getValue();
            model.addRow(new Object[]{doctor.getmLicense(), doctor.getName(), doctor.getSurname(), doctor.getMobileNum(), doctor.getSpeciali()});
        }


        // Creating the JTable and add the DefaultTableModel
        JTable table = new JTable(model);

        table.getTableHeader().setFont(new Font("Montserrat", Font.BOLD, 15));
        table.getTableHeader().setOpaque(false);
        table.getTableHeader().setBackground(new Color(0x7BB7C8));
        table.getTableHeader().setForeground(new Color(0xffffff));
        table.setEnabled(false);

        // Adding the JTable to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(0, 0, 800, 200);

        doctorListFrame = new JFrame("Doctor List");
        doctorListFrame.setSize(800, 350);
        doctorListFrame.setLocationRelativeTo(null); // center the frame
        doctorListFrame.setResizable(false);
        doctorListFrame.setLayout(null);

        // Adding a button to  sort doctors
        MyButton sortButton = new MyButton("Sort by Last Name");
        sortButton.setBounds(275, 225, 250, 50);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Creating a row sorter for the table
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

                // Sorting the table by the last name column
                sorter.setSortable(3, true);
                sorter.setSortKeys(List.of(new RowSorter.SortKey(3, SortOrder.ASCENDING)));
                table.setRowSorter(sorter);
                sortButton.setEnabled(false);
            }
        });

        doctorListFrame.add(scrollPane);
        doctorListFrame.add(sortButton);
        doctorListFrame.setVisible(true);
        doctorListFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        doctorListFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                //enable doctorlist button in homeFrame when the window is closed
                doctorListButton.setEnabled(true);

            }
        });
    }

    public static void bookConsultation() {

        DoctorList doctorList = manager.getDoctorList();

        // Creating the book consultation window
        selectDoctorFrame = new JFrame("Select Doctor");
        selectDoctorFrame.setSize(800, 650);
        selectDoctorFrame.setLocationRelativeTo(null); // center the frame
        selectDoctorFrame.setResizable(false);
        selectDoctorFrame.setLayout(null);
        selectDoctorFrame.setVisible(true);
        selectDoctorFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        selectDoctorFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                bookConsultationButton.setEnabled(true);
            }
        });

        // Adding a title label
        JLabel titleLabel = new JLabel("Select doctor and time");
        titleLabel.setBounds(230, 25, 450, 50);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0x7BB7C8));
        selectDoctorFrame.add(titleLabel);

        //Adding label for select doctor
        JLabel selectDoctorLabel = new JLabel("Select Doctor");
        selectDoctorLabel.setBounds(275, 100, 250, 50);
        selectDoctorLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        selectDoctorLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(selectDoctorLabel);


        // Iterating through hashmap and adding each doctor combo box
        JComboBox<String> doctorComboBox = new JComboBox<>();
        for (HashMap.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
            Doctor doctor = entry.getValue();
            doctorComboBox.addItem(String.valueOf(doctor.getmLicense()));
        }

        doctorComboBox.setSelectedIndex(-1); // Nothing is selected by default

        // Adding labels to show selected doctor's details
        JLabel doctorNameLabel = new JLabel("Doctor Name : ");
        doctorNameLabel.setBounds(275, 170, 400, 50);
        doctorNameLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        doctorNameLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(doctorNameLabel);

        JLabel doctorSpecializationLabel = new JLabel("Specialization : ");
        doctorSpecializationLabel.setBounds(275, 200, 400, 50);
        doctorSpecializationLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        doctorSpecializationLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(doctorSpecializationLabel);

        JLabel doctorMobileLabel = new JLabel("Mobile No : ");
        doctorMobileLabel.setBounds(275, 230, 400, 50);
        doctorMobileLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        doctorMobileLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(doctorMobileLabel);


        // Adding event listener and show the selected doctor's details
        doctorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                Doctor doctor = doctorList.getDoctorList().get(Integer.parseInt(selectedDoctor));
                String doctorDetails = doctor.getName() + " " + doctor.getSurname();
                doctorNameLabel.setText("Doctor Name : "+doctorDetails);
                doctorSpecializationLabel.setText("Specialization : " + doctor.getSpeciali());
                doctorMobileLabel.setText("Mobile No : " + doctor.getMobileNum());
            }
        });

        doctorComboBox.setBounds(275, 150, 250, 30);
        doctorComboBox.setFont(new Font("Montserrat", Font.BOLD, 20));
        doctorComboBox.setForeground(new Color(0x000000));
        selectDoctorFrame.add(doctorComboBox);

        //Adding label for select date and adding date picker for selecting date
        JLabel selectDateLabel = new JLabel("Select Date");
        selectDateLabel.setBounds(275, 300, 250, 50);
        selectDateLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        selectDateLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(selectDateLabel);

        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
        datePicker.setBounds(275, 350, 250, 30);
        selectDoctorFrame.add(datePicker);

        // Adding event listener to check whether the selected date is valid
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Date selectedDate = (Date) datePicker.getModel().getValue();

                // Check if the selected date is before the present date
                if (selectedDate.before(new Date())) {
                    JOptionPane.showMessageDialog(null, "Please select a date in the future.", "Error", JOptionPane.ERROR_MESSAGE);

                    // Reset the date to the present date
                    Calendar calendar = Calendar.getInstance();
                    model.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    model.setSelected(true);
                }
            }
        });


        //Add label for select time
        JLabel selectTimeLabel = new JLabel("Select Time");
        selectTimeLabel.setBounds(275, 400, 250, 50);
        selectTimeLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        selectTimeLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(selectTimeLabel);


        String[] options = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"}; // Array for available times
        SpinnerListModel selectTimeSpinnerModel = new SpinnerListModel(options);
        JSpinner selectTimeSpinner = new JSpinner(selectTimeSpinnerModel);
        selectTimeSpinner.setBounds(275, 440, 100, 30);
        selectDoctorFrame.add(selectTimeSpinner);

        // Adding a change listener to the spinner to check whether the selected time is valid
        selectTimeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                String value = (String) selectTimeSpinner.getValue();

                boolean valid = false;
                for (String option : options) {
                    if (option.equals(value)) {
                        valid = true;
                        break;
                    }
                }
                if (!valid) {
                    JOptionPane.showMessageDialog(null, "Please select a valid option.", "Error", JOptionPane.ERROR_MESSAGE);
                    selectTimeSpinner.setValue(selectTimeSpinner.getPreviousValue());
                }
            }
        });


        // Adding label for enter number of hours
        JLabel enterHoursLabel = new JLabel("Num of Hr");
        enterHoursLabel.setBounds(400, 400, 250, 50);
        enterHoursLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        enterHoursLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(enterHoursLabel);

        SpinnerNumberModel numOfHrSpinnerModel = new SpinnerNumberModel(1, 1, 3, 1);
        JSpinner numOfHrSpinner = new JSpinner(numOfHrSpinnerModel);

        // Adding a change listener to check whether the selected number of hours is valid.
        numOfHrSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                try {
                    // get the entered value
                    int value = (int) numOfHrSpinner.getValue();

                    // check if the entered value is valid
                    if (value > 3) {
                        // show an error
                        JOptionPane.showMessageDialog(null, "Please select a value between 1 and 3.", "Error", JOptionPane.ERROR_MESSAGE);

                        // reset the value to the previous valid value
                        numOfHrSpinner.setValue(numOfHrSpinner.getPreviousValue());
                    }
                } catch (NumberFormatException ex) {
                    // show an error
                    JOptionPane.showMessageDialog(null, "Please enter a valid number.", "Error", JOptionPane.ERROR_MESSAGE);

                    // reset the value to the previous valid value
                    numOfHrSpinner.setValue(numOfHrSpinner.getPreviousValue());
                }
            }
        });

        numOfHrSpinner.setBounds(400, 440, 100, 30);
        selectDoctorFrame.add(numOfHrSpinner);

        // Adding button for submit
        MyButton submitButton = new MyButton("Check Availability");
        submitButton.setBounds(275, 500, 250, 50);

        selectDoctorFrame.add(submitButton);

        // Adding action listener for the submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date selectedDate = (Date) datePicker.getModel().getValue();

                if (doctorComboBox.getSelectedIndex() == (-1) || selectedDate == null) {
                    JOptionPane.showMessageDialog(null, "Please enter all the details.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                    Doctor doctor = doctorList.getDoctorList().get(Integer.parseInt(selectedDoctor));
                    String userInputDate = datePicker.getJFormattedTextField().getText();

                    DateTimeFormatter appointmentDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    var temporal = appointmentDateFormatter.parse(userInputDate);
                    LocalDate appointmentDate = LocalDate.from(temporal);

                    //Getting the start time and duration as strings
                    String startTimeString = selectTimeSpinner.getValue().toString();
                    String durationString = numOfHrSpinner.getValue().toString();

                    // Parse the start time and duration strings to create LocalTime objects
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
                    LocalTime startTime = LocalTime.parse(startTimeString, formatter);
                    LocalTime endTime = startTime.plusHours(Integer.parseInt(durationString));

                    // Check whether the doctor is available
                    boolean doctorAvailability = doctor.checkAvailability(appointmentDate, startTime, endTime);
                    if(doctorAvailability){
                        selectDoctorFrame.dispose();
                        addPatientDetails(selectedDoctor, appointmentDate, startTime, endTime,Integer.parseInt(durationString));
                    }else {

                        // Check other doctors are available in the same time slot
                        boolean otherDoctorAvailability = false;
                        for (Map.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
                            if (entry.getKey() != Integer.parseInt(selectedDoctor)) {
                                otherDoctorAvailability = entry.getValue().checkAvailability(appointmentDate, startTime, endTime);
                                if (otherDoctorAvailability) {
                                    JOptionPane.showMessageDialog(null, "Selected doctor isn't available. Assigned to another doctor.", "Time slot not available.", JOptionPane.INFORMATION_MESSAGE);
                                    selectDoctorFrame.dispose();
                                    addPatientDetails(String.valueOf(entry.getKey()), appointmentDate, startTime, endTime,Integer.parseInt(durationString));
                                    break;
                                }
                            }
                        }
                        if (!otherDoctorAvailability) {
                            JOptionPane.showMessageDialog(null, "No doctor is available at the selected time.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            }
        });
    }

    public static void addPatientDetails(String selectedDoctorMLicense,LocalDate appointmentDate, LocalTime startTime, LocalTime endTime,int consultDuration) {
        JFrame addPatientFrame = new JFrame("Add Patient Details");
        addPatientFrame.setSize(600, 800);
        addPatientFrame.setResizable(false);
        addPatientFrame.setLayout(null);
        addPatientFrame.setLocationRelativeTo(null);
        addPatientFrame.setVisible(true);
        addPatientFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE); //disable the close button

        // Adding label for enter patient details
        JLabel enterPatientDetailsLabel= new JLabel("Enter Patient Details");
        enterPatientDetailsLabel.setBounds(125, 40, 400, 50);
        enterPatientDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
        enterPatientDetailsLabel.setForeground(new Color(0x7BB7C8));
        addPatientFrame.add(enterPatientDetailsLabel);


        // Adding label for patient name
        JLabel patientNameLabel = new JLabel("Patient Name");
        patientNameLabel.setBounds(165, 100, 250, 50);
        patientNameLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        addPatientFrame.add(patientNameLabel);

        // Adding text field for patient name
        JTextField patientNameTextField = new JTextField();
        patientNameTextField.setBounds(165, 140, 250, 30);
        addPatientFrame.add(patientNameTextField);

        // Adding label for patient surname
        JLabel patientSurnameLabel = new JLabel("Patient Surname");
        patientSurnameLabel.setBounds(165, 200, 250, 50);
        patientSurnameLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientSurnameLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientSurnameLabel);

        // Adding text field for patient surname
        JTextField patientSurnameTextField = new JTextField();
        patientSurnameTextField.setBounds(165, 240, 250, 30);
        addPatientFrame.add(patientSurnameTextField);

        // Adding label for patient birthday
        JLabel patientBirthDateLabel = new JLabel("Patient Birth Date");
        patientBirthDateLabel.setBounds(165, 300, 250, 50);
        patientBirthDateLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientBirthDateLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientBirthDateLabel);

        // Creating a date picker for patient birthday
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl dateOfBirthPicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        dateOfBirthPicker.setBounds(165, 340, 250, 30);
        addPatientFrame.add(dateOfBirthPicker);

        //Adding label for patient phone number
        JLabel patientPhoneLabel = new JLabel("Patient Phone Number");
        patientPhoneLabel.setBounds(165, 400, 250, 50);
        patientPhoneLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientPhoneLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientPhoneLabel);

        //Adding text field for patient phone number
        JTextField patientPhoneTextField = new JTextField();
        patientPhoneTextField.setBounds(165, 440, 250, 30);
        addPatientFrame.add(patientPhoneTextField);

        //Adding label for patient's nic
        JLabel patientNicLabel = new JLabel("Patient NIC");
        patientNicLabel.setBounds(165, 500, 250, 50);
        patientNicLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientNicLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientNicLabel);

        //Getting text field for get patient's nic
        JTextField patientNicTextField = new JTextField();
        patientNicTextField.setBounds(165, 540, 250, 30);
        addPatientFrame.add(patientNicTextField);

        //Adding button for submit
        MyButton submitButton = new MyButton("Submit");
        submitButton.setBounds(165, 630, 250, 50);
        addPatientFrame.add(submitButton);

        //Adding action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Check if the user inputs are valid
                if (patientNameTextField.getText().equals("") || patientSurnameTextField.getText().equals("") || dateOfBirthPicker.getJFormattedTextField().getText().equals("") || patientPhoneTextField.getText().equals("") || patientNicTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter all the details.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Getting the selected date from the date picker
                    UtilDateModel model = (UtilDateModel) dateOfBirthPicker.getModel();
                    Date selectedDate = model.getValue();

                    // Convert the selected date to the LocalDate format (YYYY-MM-DD)
                    LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String userInputPatientName = patientNameTextField.getText();
                    String userInputPatientSurname = patientSurnameTextField.getText();
                    LocalDate userInputPatientBirthDate = localDate;
                    int userInputPatientPhone = Integer.parseInt(patientPhoneTextField.getText());
                    String userInputPatientNic = patientNicTextField.getText();
                    int cost = 0;

                    Patient patient = new Patient(userInputPatientName, userInputPatientSurname, userInputPatientBirthDate, userInputPatientPhone, userInputPatientNic); //create a new patient object using collected details
                    Consultation consultation = new Consultation(appointmentDate,startTime,endTime,cost,patient); //create a new consultation object using collected details

                    // Check whether patient is already registered and decide cost
                    if (pastPatients.containsKey(userInputPatientNic)) {
                        consultation.setCost(25*consultDuration);
                    } else {
                        consultation.setCost(15*consultDuration);
                        pastPatients.put(userInputPatientNic,patient);
                    }

                    consultationList.put(consultation.getConsultationID(),consultation);
                    DoctorList doctorList = manager.getDoctorList();

                    //Adding consultation to the doctor
                    for (HashMap.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
                        if (entry.getKey() == Integer.parseInt(selectedDoctorMLicense)) {
                            entry.getValue().addConsultation(consultation);
                            consultation.setAssignedDoctor(entry.getValue());
                        }
                    }

                    addPatientFrame.dispose(); // Close the add patient frame
                    showConsultationDetails(consultation); // Show the consultation details.
                }
            }
        });

    }

    public static void showConsultationDetails(Consultation consultation){

        JFrame consultationDetailsFrame = new JFrame("Appointment Details");
        consultationDetailsFrame.setSize(500, 740);
        consultationDetailsFrame.setLayout(null);
        consultationDetailsFrame.setVisible(true);
        consultationDetailsFrame.setResizable(false);
        consultationDetailsFrame.setLocationRelativeTo(null);
        consultationDetailsFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

        //Adding label for appointment details
        JLabel consultationDetailsLabel = new JLabel("Appointment Details");
        consultationDetailsLabel.setBounds(100, 50, 300, 50);
        consultationDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        consultationDetailsLabel.setForeground(new Color(0x7BB7C8));
        consultationDetailsFrame.add(consultationDetailsLabel);


        //Showing assigned doctor details
        JLabel assignedDoctorLabel = new JLabel("Assigned Doctor");
        assignedDoctorLabel.setBounds(100, 95, 250, 50);
        assignedDoctorLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(assignedDoctorLabel);

        JTextArea assignedDoctorTextArea = new JTextArea();
        assignedDoctorTextArea.setBounds(100, 135, 250, 70);
        assignedDoctorTextArea.setEditable(false);
        assignedDoctorTextArea.setBackground(new Color(0xeeeeee));

        assignedDoctorTextArea.append(consultation.getAssignedDoctor().toString());
        consultationDetailsFrame.add(assignedDoctorTextArea);


        //Showing assigned patient details
        JLabel assignedPatientLabel = new JLabel("Assigned Patient");
        assignedPatientLabel.setBounds(100, 205, 250, 50);
        assignedPatientLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(assignedPatientLabel);

        JTextArea assignedPatientTextArea = new JTextArea();
        assignedPatientTextArea.setBounds(100, 245, 250, 70);
        assignedPatientTextArea.setEditable(false);
        assignedPatientTextArea.setBackground(new Color(0xeeeeee));

        assignedPatientTextArea.append(consultation.getPatient().toString());
        consultationDetailsFrame.add(assignedPatientTextArea);


        //Showing consultation details
        JLabel consultationDetailsLabel2 = new JLabel("Consultation Details");
        consultationDetailsLabel2.setBounds(100, 325, 300, 50);
        consultationDetailsLabel2.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(consultationDetailsLabel2);

        JTextArea consultationDetailsTextArea = new JTextArea();
        consultationDetailsTextArea.setBounds(100, 365, 400, 80);
        consultationDetailsTextArea.setEditable(false);
        consultationDetailsTextArea.setBackground(new Color(0xeeeeee));

        consultationDetailsTextArea.append(consultation.toString());
        consultationDetailsFrame.add(consultationDetailsTextArea);

        //Adding label to add notes
        JLabel addNotesLabel = new JLabel("Add Notes");
        addNotesLabel.setBounds(100, 455, 250, 50);
        addNotesLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(addNotesLabel);

        //Adding text area get additional notes
        JTextArea addNotesTextArea = new JTextArea();
        addNotesTextArea.setBounds(100, 500, 250, 50);
        consultationDetailsFrame.add(addNotesTextArea);

        //Adding button to add image
        MyButton addImage = new MyButton("Add Image");
        addImage.setBounds(100, 560, 100, 30);
        addImage.setFont(new Font("Montserrat", Font.BOLD, 8));
        consultationDetailsFrame.add(addImage);


        //Adding event listener to add image button
        addImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                FileNameExtensionFilter filter = new FileNameExtensionFilter("*.Images", "jpg","gif","png");
                fileChooser.addChoosableFileFilter(filter);
                fileChooser.setMultiSelectionEnabled(false);
                int result = fileChooser.showSaveDialog(null);
                if (result == JFileChooser.APPROVE_OPTION) {
                    // Getting path of the selected file
                    File selectedFile = fileChooser.getSelectedFile();
                    String filePath = selectedFile.getAbsolutePath();
                    Path sourcePath = Path.of(filePath);

                    // Copy the file to the image directory
                    Path destinationPath = Path.of("images/"+consultation.getConsultationID()+".jpg");
                    consultation.setImagePath("images/"+consultation.getConsultationID()+".jpg");

                    try {
                        Files.copy(sourcePath, destinationPath);
                        JOptionPane.showMessageDialog(null, "Image added successfully.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    } catch (IOException ex) {
                        try {
                            Files.copy(sourcePath, destinationPath, StandardCopyOption.REPLACE_EXISTING);
                        } catch (IOException ioException) {
                            ioException.printStackTrace();
                        }
                    }

                }else if(result == JFileChooser.CANCEL_OPTION){
                    JOptionPane.showMessageDialog(null, "No image selected.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        System.out.println("test "+ consultation.getImage());


        //Adding button to submit notes
        MyButton bookConsultation = new MyButton("Book Consultation");
        bookConsultation.setBounds(100, 610, 250, 50);
        consultationDetailsFrame.add(bookConsultation);

        //Adding action listener to book consultation button
        bookConsultation.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String notes = addNotesTextArea.getText();
                consultation.setAdditionalNote(notes);
                JOptionPane.showMessageDialog(null, "Consultation Booked", "Success", JOptionPane.INFORMATION_MESSAGE);
                consultationDetailsFrame.dispose();
                bookConsultationButton.setEnabled(true);
            }
        });

    }

    public static void viewConsultations(){
        JFrame viewConsultationsFrame = new JFrame("View Consultations");
        viewConsultationsFrame.setSize(1366, 768);
        viewConsultationsFrame.setLayout(null);
        viewConsultationsFrame.setVisible(true);
        viewConsultationsFrame.setResizable(false);
        viewConsultationsFrame.setLocationRelativeTo(null);
        viewConsultationsFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        viewConsultationsFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                viewConsultationsButton.setEnabled(true);
            }
        });


        //Adding label for view consultations
        JLabel viewConsultationsLabel = new JLabel("View Consultations");
        viewConsultationsLabel.setBounds(540, 40, 350, 50);
        viewConsultationsLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
        viewConsultationsLabel.setForeground(new Color(0x7BB7C8));
        viewConsultationsFrame.add(viewConsultationsLabel);

        //Adding table to show consultations
        String[] columnNames = {"Consultation ID", "Patient Name", "Patient Surname", "Patient Phone", "Consultation Date", "Consultation Time","Doctor", };
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        JTable consultationsTable = new JTable(model);
        consultationsTable.setBounds(75, 100, 1200, 500);
        consultationsTable.setRowHeight(30);
        consultationsTable.setFont(new Font("Montserrat", Font.PLAIN, 12));
        consultationsTable.getTableHeader().setFont(new Font("Montserrat", Font.BOLD, 15));
        consultationsTable.getTableHeader().setOpaque(false);
        consultationsTable.getTableHeader().setBackground(new Color(0x7BB7C8));
        consultationsTable.getTableHeader().setForeground(new Color(0xffffff));
        consultationsTable.setShowGrid(false);
        consultationsTable.setShowVerticalLines(true);
        consultationsTable.setShowHorizontalLines(true);

        //Adding details to table from consultations hashmap
        for (Map.Entry<String, Consultation> entry : consultationList.entrySet()) {
            Consultation consultation = entry.getValue();
            model.addRow(new Object[]{consultation.getConsultationID(), consultation.getPatient().getName(), consultation.getPatient().getSurname(), consultation.getPatient().getMobileNum(), consultation.getDate(), consultation.getStartTime(), ("Dr. "+ consultation.getAssignedDoctor().getName()+ " " + consultation.getAssignedDoctor().getSurname())});
        }

        //Adding scroll pane to table
        JScrollPane scrollPane = new JScrollPane(consultationsTable);
        scrollPane.setBounds(75, 100, 1200, 500);
        viewConsultationsFrame.add(scrollPane);

        //Adding button to view consultation details
        MyButton viewConsultationDetailsButton = new MyButton("More Details");
        viewConsultationDetailsButton.setBounds(450, 630, 200, 50);
        viewConsultationsFrame.add(viewConsultationDetailsButton);

        //Adding button to delete consultation
        MyButton deleteConsultationButton = new MyButton("Delete");
        deleteConsultationButton.setBounds(750, 630, 200, 50);
        viewConsultationsFrame.add(deleteConsultationButton);

        //Adding action listener to delete consultation button
        deleteConsultationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = consultationsTable.getSelectedRow();
                if (selectedRow == -1) {
                    JOptionPane.showMessageDialog(null, "Please select a consultation to delete", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    String consultationID = consultationsTable.getValueAt(selectedRow, 0).toString();
                    Consultation consultation = consultationList.get(consultationID);

                    //Removing image from images folder
                    try {
                        Files.deleteIfExists(Path.of(consultation.getImage()));
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }

                    consultationList.get(consultationID).getAssignedDoctor().removeConsultation(consultationList.get(consultationID));//Removing consultation from doctor's consultation list
                    consultationList.remove(consultationID);//Removing consultation from hashmap
                    model.removeRow(selectedRow);//Removing consultation from table
                    JOptionPane.showMessageDialog(null, "Consultation deleted", "Success", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        //Adding action listener to view consultation details button
        viewConsultationDetailsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = consultationsTable.getSelectedRow();
                if(selectedRow == -1){
                    JOptionPane.showMessageDialog(null, "Please select a consultation", "Error", JOptionPane.ERROR_MESSAGE);
                }else{
                    String consultationID = consultationsTable.getValueAt(selectedRow, 0).toString();
                    Consultation consultation = consultationList.get(consultationID);
                    viewConsultationDetails(consultation);
                }
            }
        });
    }

    public static void viewConsultationDetails(Consultation consultation){
        JFrame viewConsultationDetails = new JFrame("View Consultation Details");
        viewConsultationDetails.setSize(500, 740);
        viewConsultationDetails.setLayout(null);
        viewConsultationDetails.setVisible(true);
        viewConsultationDetails.setResizable(false);
        viewConsultationDetails.setLocationRelativeTo(null);
        viewConsultationDetails.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        //Adding label for appointment details
        JLabel consultationDetailsLabel = new JLabel("Consultation Details");
        consultationDetailsLabel.setBounds(100, 50, 300, 50);
        consultationDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        consultationDetailsLabel.setForeground(new Color(0x7BB7C8));
        viewConsultationDetails.add(consultationDetailsLabel);


        //Showing assigned doctor details
        JLabel assignedDoctorLabel = new JLabel("Assigned Doctor");
        assignedDoctorLabel.setBounds(105, 95, 250, 50);
        assignedDoctorLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        viewConsultationDetails.add(assignedDoctorLabel);

        JTextArea assignedDoctorTextArea = new JTextArea();
        assignedDoctorTextArea.setBounds(105, 135, 250, 70);
        assignedDoctorTextArea.setEditable(false);
        assignedDoctorTextArea.setBackground(new Color(0xeeeeee));

        assignedDoctorTextArea.append(consultation.getAssignedDoctor().toString());
        viewConsultationDetails.add(assignedDoctorTextArea);


        //Showing assigned patient details
        JLabel assignedPatientLabel = new JLabel("Assigned Patient");
        assignedPatientLabel.setBounds(105, 205, 250, 50);
        assignedPatientLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        viewConsultationDetails.add(assignedPatientLabel);

        JTextArea assignedPatientTextArea = new JTextArea();
        assignedPatientTextArea.setBounds(105, 245, 250, 70);
        assignedPatientTextArea.setEditable(false);
        assignedPatientTextArea.setBackground(new Color(0xeeeeee));

        assignedPatientTextArea.append(consultation.getPatient().toString());
        viewConsultationDetails.add(assignedPatientTextArea);


        //Showing consultation details
        JLabel consultationDetailsLabel2 = new JLabel("Consultation Details");
        consultationDetailsLabel2.setBounds(105, 325, 250, 50);
        consultationDetailsLabel2.setFont(new Font("Montserrat", Font.BOLD, 18));
        viewConsultationDetails.add(consultationDetailsLabel2);

        JTextArea appointmentDetailsTextArea = new JTextArea();
        appointmentDetailsTextArea.setBounds(105, 365, 400, 80);
        appointmentDetailsTextArea.setEditable(false);
        appointmentDetailsTextArea.setBackground(new Color(0xeeeeee));

        appointmentDetailsTextArea.append(consultation.toString());
        viewConsultationDetails.add(appointmentDetailsTextArea);

        //Showing consultation notes
        JLabel consultationNotesLabel = new JLabel("Consultation Notes");
        consultationNotesLabel.setBounds(105, 455, 250, 50);
        consultationNotesLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        viewConsultationDetails.add(consultationNotesLabel);

        JTextArea consultationNotesTextArea = new JTextArea();
        consultationNotesTextArea.setBounds(105, 495, 250, 80);
        consultationNotesTextArea.setEditable(false);
        consultationNotesTextArea.setBackground(new Color(0xeeeeee));

        consultationNotesTextArea.setText(consultation.getAdditionalNote());
        viewConsultationDetails.add(consultationNotesTextArea);

        //Adding button to view image saved in consultation
        MyButton viewImage = new MyButton("View Image");
        viewImage.setBounds(105, 585, 250, 50);
        viewConsultationDetails.add(viewImage);

        //Remove view image button if there isn't any image saved in consultation
        if (consultation.getImage() == null) {
            viewImage.setVisible(false);
        }
        //Adding action listener to view image button
        viewImage.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                viewImage(consultation.getImage());
            }
        });
    }

    private static void viewImage(String image) {
        JFrame viewImage = new JFrame("View Image");

        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon(image);
        imageLabel.setIcon(imageIcon);
        viewImage.add(imageLabel);

        viewImage.pack();
        viewImage.setVisible(true);
        viewImage.setLocationRelativeTo(null);
        viewImage.setResizable(false);
        viewImage.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);


    }
}





//Formatter class for the date picker
class DateLabelFormatter extends JFormattedTextField.AbstractFormatter {
    private String datePattern = "yyyy-MM-dd";
    private SimpleDateFormat dateFormatter = new SimpleDateFormat(datePattern);
    @Override
    public Object stringToValue(String text) throws ParseException {
        return dateFormatter.parseObject(text);
    }
    @Override
    public String valueToString(Object value) throws ParseException {
        if (value != null) {
            Calendar cal = (Calendar) value;
            return dateFormatter.format(cal.getTime());
        }
        return "";
    }
}


