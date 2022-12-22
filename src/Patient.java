import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class Patient extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    private int patientID;

    public Patient(String name, String surname, LocalDate dob, int mobileNum, String nic, int patientID) {
        super(name, surname, dob, mobileNum, nic);
        this.patientID = patientID;
    }

    public int getPatientID() {
        return patientID;
    }

    public void setPatientID(int patientID) {
        this.patientID = patientID;
    }
}
