import java.io.Serializable;
import java.time.LocalDate;

public class Patient extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    private String patientID;

    public Patient(String name, String surname, LocalDate dob, int mobileNum, String nic) {
        super(name, surname, dob, mobileNum, nic);
        this.patientID = nic;
    }

    public String getPatientID() {
        return patientID;
    }

    @Override
    public String toString() {
        return "Name : " + getName() + " " + getSurname() +
                "\nDOB : " + getDob() +
                "\nMobile No : " + getMobileNum() +
                "\nNIC : " + getNic();
    }
}
