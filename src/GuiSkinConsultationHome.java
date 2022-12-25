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
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;
import javax.swing.JButton;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;

public class GuiSkinConsultationHome {

    static JFrame homeFrame;
    static JFrame doctorListFrame;

    static JFrame selectDoctorFrame;


    static WestminsterSkinConsultationManager manager = new WestminsterSkinConsultationManager();

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
        selectDoctorFrame.setSize(800, 768);
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

        JLabel doctorNameLabel = new JLabel();
        doctorNameLabel.setBounds(275, 170, 400, 50);
        doctorNameLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        doctorNameLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(doctorNameLabel);

        JLabel doctorSpecializationLabel = new JLabel();
        doctorSpecializationLabel.setBounds(275, 200, 400, 50);
        doctorSpecializationLabel.setFont(new Font("Montserrat", Font.BOLD, 20));
        doctorSpecializationLabel.setForeground(new Color(0x000000));
        selectDoctorFrame.add(doctorSpecializationLabel);

        JLabel doctorMobileLabel = new JLabel();
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
                doctorNameLabel.setText(doctorDetails);
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

        JTextArea textArea = new JTextArea();

        //add action listener for submit button
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                Date selectedDate = (Date) datePicker.getModel().getValue();

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
                    String userInputTime = (String) selectTimeSpinner.getValue();
                    int userInputNumOfHr = (int) numOfHrSpinner.getValue();


                    //show the user input in the text area
                    String userInput = "Doctor: " + userInputDoctor + "" + "Date: " + userInputDate + "" + "Time: " + userInputTime + "" + "Num of Hr: " + userInputNumOfHr;
                    textArea.setText(userInput);

                }
            }
        });
        textArea.setBounds(275, 600, 250, 100);
        selectDoctorFrame.add(textArea);
    }
}






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


