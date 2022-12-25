import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import javax.swing.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GuiSkinConsultationHome {

    static JFrame homeFrame;
    static JFrame doctorListFrame;

    static JFrame selectDoctorFrame;


    static WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

    static HashMap<String, Patient> pastPatients = new HashMap<>();

    public static void main(String[] args) {
        homeFrame = new JFrame("Skin Consultation Home"); // Create a new JFrame

        homeFrame.setSize(1366, 768);
        homeFrame.setLocationRelativeTo(null); // center the frame
        homeFrame.setResizable(false);
        homeFrame.setLayout(null);
        homeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        // create a label with the background image
        JLabel background = new JLabel(new ImageIcon("background.png"));
        background.setBounds(0, 0, 1366, 768);

        // create a styled labal and add consultation name above all buttons
        JLabel consultationName = new JLabel("Westminster Skin");
        JLabel consultationName2 = new JLabel("Consultation");

        consultationName.setBounds(800, 120, 1366, 100);
        consultationName.setFont(new Font("Montserrat", Font.BOLD, 50));
        consultationName.setForeground(new Color(0x7BB7C8));

        consultationName2.setBounds(860, 190, 1366, 100);
        consultationName2.setFont(new Font("Montserrat", Font.BOLD, 50));
        consultationName2.setForeground(new Color(0x7BB7C8));


        // create the buttons
        MyButton doctorListButton = new MyButton("Doctor List");
        MyButton bookConsultationButton = new MyButton("Book Consultation");
        MyButton viewConsultationsButton = new MyButton("View Consultations");


        doctorListButton.setBounds(925, 310, 230, 50);
        bookConsultationButton.setBounds(925, 410, 230, 50);
        viewConsultationsButton.setBounds(925, 510, 230, 50);

        // add the buttons to the frame
        homeFrame.add(doctorListButton);
        homeFrame.add(consultationName);
        homeFrame.add(consultationName2);
        homeFrame.add(bookConsultationButton);
        homeFrame.add(viewConsultationsButton);
        homeFrame.add(background);
        homeFrame.setVisible(true); // Display the frame

        doctorListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create the doctor list window
                showDoctorsList();
            }
        });

        bookConsultationButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create the doctor list window
                bookConsultation();
            }
        });
    }

    public void dispose() {
        homeFrame.dispose();
    }


    public static class MyButton extends JButton {

        public MyButton(String text) {
            super(text);

            // set font, size, and color for button text
            this.setFont(new Font("Montserrat", Font.BOLD, 18));
            this.setForeground(Color.WHITE);

            this.setBackground(new Color(0x7BB7C8));
            this.setSize(150, 50);


            this.setFocusPainted(false);


        }
    }

    public static void showDoctorsList() {

        manager.getDoctorList().toString();
        // Create the DefaultTableModel
        DefaultTableModel model = new DefaultTableModel();

// Add columns to the DefaultTableModel
        model.addColumn("Medical License Number");
        model.addColumn("Name");
        model.addColumn("Surname");
        model.addColumn("Mobile Number");
        model.addColumn("Specialization");

        DoctorList doctorList = manager.getDoctorList();
// Iterate through the doctorList HashMap and add each Doctor object as a row to the DefaultTableModel
        for (HashMap.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
            Doctor doctor = entry.getValue();
            model.addRow(new Object[]{doctor.getmLicense(), doctor.getName(), doctor.getSurname(), doctor.getMobileNum(), doctor.getSpeciali()});
        }


// Create the JTable and add the DefaultTableModel
        JTable table = new JTable(model);

        table.setEnabled(false);
// Add the JTable to a JScrollPane
        JScrollPane scrollPane = new JScrollPane(table);

        scrollPane.setBounds(0, 0, 800, 200);

        doctorListFrame = new JFrame("Doctor List");
        doctorListFrame.setSize(800, 350);
        doctorListFrame.setLocationRelativeTo(null); // center the frame
        doctorListFrame.setResizable(false);
        doctorListFrame.setLayout(null);

        MyButton sortButton = new MyButton("Sort by Last Name");
        sortButton.setBounds(275, 225, 250, 50);
        sortButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // create a row sorter for the table
                TableRowSorter<TableModel> sorter = new TableRowSorter<>(model);

                // sort the table by the last name column
                sorter.setSortable(3, true);
                sorter.setSortKeys(List.of(new RowSorter.SortKey(3, SortOrder.ASCENDING)));
                table.setRowSorter(sorter);
                sortButton.setEnabled(false);
            }
        });

// Add the JScrollPane to your GUI
        doctorListFrame.add(scrollPane);
        doctorListFrame.add(sortButton);
        doctorListFrame.setVisible(true);
    }

    public static void bookConsultation() {
        DoctorList doctorList = manager.getDoctorList();
        // create the book consultation window
        selectDoctorFrame = new JFrame("Select Doctor");
        selectDoctorFrame.setSize(800, 650);
        selectDoctorFrame.setLocationRelativeTo(null); // center the frame
        selectDoctorFrame.setResizable(false);
        selectDoctorFrame.setLayout(null);
        selectDoctorFrame.setVisible(true);

        // add a title label
        JLabel titleLabel = new JLabel("Select doctor and time");
        titleLabel.setBounds(230, 25, 450, 50);
        titleLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
        titleLabel.setForeground(new Color(0x7BB7C8));
        selectDoctorFrame.add(titleLabel);

        //add lable for select doctor and add combobox using doctorList hashmap
        JLabel selectDoctorLabel = new JLabel("Select Doctor");
        selectDoctorLabel.setBounds(275, 100, 250, 50);
        selectDoctorLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        selectDoctorLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(selectDoctorLabel);


        JComboBox<String> doctorComboBox = new JComboBox<>();
        for (HashMap.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
            Doctor doctor = entry.getValue();
            doctorComboBox.addItem(String.valueOf(doctor.getmLicense()));
        }

        doctorComboBox.setSelectedIndex(-1);

        //show doctors other details in a lable after selecting a doctor

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

        //add lable for select date and add date picker
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

        // get the user input date from the date picker
        String userInputDate = datePicker.getJFormattedTextField().getText();


        //show selected date in console
        datePicker.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // parse the user input date into a LocalDate object
                // get the selected date from the date picker
                Date selectedDate = (Date) datePicker.getModel().getValue();

// convert the selected date to a LocalDate object
                LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();


                // check if the selected date is before the present date
                if (selectedDate.before(new Date())) {
                    // show a message
                    JOptionPane.showMessageDialog(null, "Please select a date in the future.", "Error", JOptionPane.ERROR_MESSAGE);

                    // reset the date to the present date
                    Calendar calendar = Calendar.getInstance();
                    model.setDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DATE));
                    model.setSelected(true);
                }
            }
        });


        //add lable for select time
        JLabel selectTimeLabel = new JLabel("Select Time");
        selectTimeLabel.setBounds(275, 400, 250, 50);
        selectTimeLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        selectTimeLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(selectTimeLabel);

        String[] options = {"9:00", "10:00", "11:00", "12:00", "13:00", "14:00", "15:00", "16:00", "17:00"};
        // create the spinner model
        SpinnerListModel selectTimeSpinnerModel = new SpinnerListModel(options);

        // create the spinner
        JSpinner selectTimeSpinner = new JSpinner(selectTimeSpinnerModel);
        selectTimeSpinner.setBounds(275, 440, 100, 30);
        selectDoctorFrame.add(selectTimeSpinner);

        // add a change listener to the spinner
        selectTimeSpinner.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                // get the entered value
                String value = (String) selectTimeSpinner.getValue();

                // check if the entered value is a valid option
                boolean valid = false;
                for (String option : options) {
                    if (option.equals(value)) {
                        valid = true;
                        break;
                    }
                }

                if (!valid) {
                    // show an error
                    JOptionPane.showMessageDialog(null, "Please select a valid option.", "Error", JOptionPane.ERROR_MESSAGE);

                    // reset the value to the previous valid value
                    selectTimeSpinner.setValue(selectTimeSpinner.getPreviousValue());
                }
            }
        });

//        add label for enter number of hours
        JLabel enterHoursLabel = new JLabel("Num of Hr");
        enterHoursLabel.setBounds(400, 400, 250, 50);
        enterHoursLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        enterHoursLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(enterHoursLabel);

        // create the spinner model
        SpinnerNumberModel numOfHrSpinnerModel = new SpinnerNumberModel(1, 1, 3, 1);

        // create the spinner
        JSpinner numOfHrSpinner = new JSpinner(numOfHrSpinnerModel);

        // add a change listener to the spinner
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

        //add button for submit
        MyButton submitButton = new MyButton("Check Availability");
        submitButton.setBounds(275, 500, 250, 50);

        selectDoctorFrame.add(submitButton);

        //add action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date selectedDate = (Date) datePicker.getModel().getValue();
                System.out.println(selectedDate);

// convert the selected date to a LocalDate object

                //check if the user input is valid
                if (doctorComboBox.getSelectedIndex() == (-1) || selectedDate == null) {
                    JOptionPane.showMessageDialog(null, "Please enter all the details.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {

                    //get the user input
                    String selectedDoctor = (String) doctorComboBox.getSelectedItem();
                    Doctor doctor = doctorList.getDoctorList().get(Integer.parseInt(selectedDoctor));
                    String userInputDoctor = doctor.getName() + " " + doctor.getSurname();
                    String userInputDate = datePicker.getJFormattedTextField().getText();


                    // create a DateTimeFormatter object using the pattern
                    DateTimeFormatter appointmentDateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    var temporal = appointmentDateFormatter.parse(userInputDate);
                    LocalDate appointmentDate = LocalDate.from(temporal);

                    String userInputTime = (String) selectTimeSpinner.getValue();
                    int userInputNumOfHr = (int) numOfHrSpinner.getValue();

                    // get the start time and duration as strings
                    String startTimeString = selectTimeSpinner.getValue().toString();
                    String durationString = numOfHrSpinner.getValue().toString();

                    // parse the start time and duration strings to create LocalTime objects
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("H:mm");
                    LocalTime startTime = LocalTime.parse(startTimeString, formatter);
                    LocalTime endTime = startTime.plusHours(Integer.parseInt(durationString));

                    //Check whether the doctor is available considering the appointment date, start time and duration
                    boolean doctorAvailability = doctor.checkAvailability(appointmentDate, startTime, endTime);
                    System.out.println(doctorAvailability);
                    if(doctorAvailability){
                        selectDoctorFrame.dispose();
                        addPatientDetails(selectedDoctor, appointmentDate, startTime, endTime,Integer.parseInt(durationString));
                    }else {

                        //check other doctors availability
                        boolean otherDoctorAvailability = false;
                        for (Map.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
                            if (entry.getKey() != Integer.parseInt(selectedDoctor)) {
                                otherDoctorAvailability = entry.getValue().checkAvailability(appointmentDate, startTime, endTime);
                                if (otherDoctorAvailability) {
                                    JOptionPane.showMessageDialog(null, "Selected doctor not available. Assigned to another doctor.", "Time slot not available.", JOptionPane.INFORMATION_MESSAGE);
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

        //add label for enter patient details
        JLabel enterPatientDetailsLabel= new JLabel("Enter Patient Details");
        enterPatientDetailsLabel.setBounds(125, 40, 400, 50);
        enterPatientDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 30));
        enterPatientDetailsLabel.setForeground(new Color(0x7BB7C8));
        addPatientFrame.add(enterPatientDetailsLabel);


        //add label for patient name
        JLabel patientNameLabel = new JLabel("Patient Name");
        patientNameLabel.setBounds(165, 100, 250, 50);
        patientNameLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        addPatientFrame.add(patientNameLabel);

        //add text field for patient name
        JTextField patientNameTextField = new JTextField();
        patientNameTextField.setBounds(165, 140, 250, 30);
        addPatientFrame.add(patientNameTextField);

        //add label for patient surname
        JLabel patientSurnameLabel = new JLabel("Patient Surname");
        patientSurnameLabel.setBounds(165, 200, 250, 50);
        patientSurnameLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientSurnameLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientSurnameLabel);

        //add text field for patient surname
        JTextField patientSurnameTextField = new JTextField();
        patientSurnameTextField.setBounds(165, 240, 250, 30);
        addPatientFrame.add(patientSurnameTextField);

        //add label for patient birthdate
        JLabel patientBirthDateLabel = new JLabel("Patient Birth Date");
        patientBirthDateLabel.setBounds(165, 300, 250, 50);
        patientBirthDateLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientBirthDateLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientBirthDateLabel);

        // Create a date picker
        UtilDateModel model = new UtilDateModel();
        Properties p = new Properties();
        p.put("text.today", "Today");
        p.put("text.month", "Month");
        p.put("text.year", "Year");
        JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
        JDatePickerImpl dateOfBirthPicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());

        dateOfBirthPicker.setBounds(165, 340, 250, 30);
        addPatientFrame.add(dateOfBirthPicker);

        //add label for patient phone number
        JLabel patientPhoneLabel = new JLabel("Patient Phone Number");
        patientPhoneLabel.setBounds(165, 400, 250, 50);
        patientPhoneLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientPhoneLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientPhoneLabel);

        //add text field for patient phone number
        JTextField patientPhoneTextField = new JTextField();
        patientPhoneTextField.setBounds(165, 440, 250, 30);
        addPatientFrame.add(patientPhoneTextField);

        //get users nic lable
        JLabel patientNicLabel = new JLabel("Patient NIC");
        patientNicLabel.setBounds(165, 500, 250, 50);
        patientNicLabel.setFont(new Font("Montserrat", Font.BOLD, 12));
        patientNicLabel.setForeground(new Color(0x000000));
        addPatientFrame.add(patientNicLabel);

        //get users nic text field as string
        JTextField patientNicTextField = new JTextField();
        patientNicTextField.setBounds(165, 540, 250, 30);
        addPatientFrame.add(patientNicTextField);

        //add button for submit
        MyButton submitButton = new MyButton("Submit");
        submitButton.setBounds(165, 630, 250, 50);
        addPatientFrame.add(submitButton);

        //add action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //check if the user input is valid
                if (patientNameTextField.getText().equals("") || patientSurnameTextField.getText().equals("") || dateOfBirthPicker.getJFormattedTextField().getText().equals("") || patientPhoneTextField.getText().equals("") || patientNicTextField.getText().equals("")) {
                    JOptionPane.showMessageDialog(null, "Please enter all the details.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // Get the selected date from the date picker
                    UtilDateModel model = (UtilDateModel) dateOfBirthPicker.getModel();
                    Date selectedDate = model.getValue();
                    System.out.println("Selected date: " + selectedDate);

                    // Convert the selected date to the LocalDate format (YYYY-MM-DD)
                    LocalDate localDate = selectedDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    System.out.println("Converted date: " + localDate);
                    String userInputPatientName = patientNameTextField.getText();
                    String userInputPatientSurname = patientSurnameTextField.getText();
                    LocalDate userInputPatientBirthDate = localDate;
                    int userInputPatientPhone = Integer.parseInt(patientPhoneTextField.getText());
                    String userInputPatientNic = patientNicTextField.getText();
                    int cost = 0;

                    Patient patient = new Patient(userInputPatientName, userInputPatientSurname, userInputPatientBirthDate, userInputPatientPhone, userInputPatientNic); //create a new patient object using collected details
                    Consultation consultation = new Consultation(appointmentDate,startTime,endTime,cost,patient); //create a new consultation object using collected details

                    //Check whether patient is already registered and decide cost
                    if (pastPatients.containsKey(userInputPatientNic)) {
                        consultation.setCost(25*consultDuration);
                    } else {
                        consultation.setCost(15*consultDuration);
                        pastPatients.put(userInputPatientNic,patient);
                    }


                    DoctorList doctorList = manager.getDoctorList();


                    //check doctor using key and add consultation to the doctor
                    for (HashMap.Entry<Integer, Doctor> entry : doctorList.getDoctorList().entrySet()) {
                        if (entry.getKey() == Integer.parseInt(selectedDoctorMLicense)) {
                            entry.getValue().addConsultation(consultation);
                            consultation.setAssignedDoctor(entry.getValue());
                            System.out.println(entry.getValue().toString());
                        }
                    }



                    //show the user input in the text area
                    String userInput = "Patient Name: " + userInputPatientName + "" + "Patient Surname: " + userInputPatientSurname + "" + "Patient Birth Date: " + userInputPatientBirthDate.toString() + "" + "Patient Phone: " + userInputPatientPhone + "" + "Patient NIC: " + userInputPatientNic;
                    JOptionPane.showMessageDialog(null, userInput, "User Input", JOptionPane.INFORMATION_MESSAGE);

                    addPatientFrame.dispose();
                    showConsultationDetails(consultation);
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

        //add label for appointment details
        JLabel appointmentDetailsLabel = new JLabel("Appointment Details");
        appointmentDetailsLabel.setBounds(100, 50, 300, 50);
        appointmentDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 25));
        appointmentDetailsLabel.setForeground(new Color(0x7BB7C8));
        consultationDetailsFrame.add(appointmentDetailsLabel);


        //Show assigned doctor details
        JLabel assignedDoctorLabel = new JLabel("Assigned Doctor");
        assignedDoctorLabel.setBounds(125, 95, 250, 50);
        assignedDoctorLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(assignedDoctorLabel);

        JTextArea assignedDoctorTextArea = new JTextArea();
        assignedDoctorTextArea.setBounds(125, 135, 250, 70);
        assignedDoctorTextArea.setEditable(false);
        assignedDoctorTextArea.setBackground(new Color(0xeeeeee));

        assignedDoctorTextArea.append(consultation.getAssignedDoctor().toString());
        consultationDetailsFrame.add(assignedDoctorTextArea);


        //Show assigned patient details
        JLabel assignedPatientLabel = new JLabel("Assigned Patient");
        assignedPatientLabel.setBounds(125, 205, 250, 50);
        assignedPatientLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(assignedPatientLabel);

        JTextArea assignedPatientTextArea = new JTextArea();
        assignedPatientTextArea.setBounds(125, 245, 250, 70);
        assignedPatientTextArea.setEditable(false);
        assignedPatientTextArea.setBackground(new Color(0xeeeeee));

        assignedPatientTextArea.append(consultation.getPatient().toString());
        consultationDetailsFrame.add(assignedPatientTextArea);


        //Show consultation details
        JLabel consiltationDetailsLabel = new JLabel("Appointment Details");
        consiltationDetailsLabel.setBounds(125, 325, 250, 50);
        consiltationDetailsLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(consiltationDetailsLabel);

        JTextArea appointmentDetailsTextArea = new JTextArea();
        appointmentDetailsTextArea.setBounds(125, 365, 250, 80);
        appointmentDetailsTextArea.setEditable(false);
        appointmentDetailsTextArea.setBackground(new Color(0xeeeeee));

        appointmentDetailsTextArea.append(consultation.toString());
        consultationDetailsFrame.add(appointmentDetailsTextArea);

        //add lable to add notes
        JLabel addNotesLabel = new JLabel("Add Notes");
        addNotesLabel.setBounds(125, 455, 250, 50);
        addNotesLabel.setFont(new Font("Montserrat", Font.BOLD, 18));
        consultationDetailsFrame.add(addNotesLabel);

        //add text area to add notes
        JTextArea addNotesTextArea = new JTextArea();
        addNotesTextArea.setBounds(125, 500, 250, 50);
        consultationDetailsFrame.add(addNotesTextArea);

        // add button to submit notes
        MyButton submitNotesButton = new MyButton("Book Consultation");
        submitNotesButton.setBounds(125, 570, 250, 50);
        consultationDetailsFrame.add(submitNotesButton);

        submitNotesButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String notes = addNotesTextArea.getText();
                consultation.setAdditionalNote(notes);
                JOptionPane.showMessageDialog(null, "Consultation Booked", "Success", JOptionPane.INFORMATION_MESSAGE);
                consultationDetailsFrame.dispose();
            }
        });


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


