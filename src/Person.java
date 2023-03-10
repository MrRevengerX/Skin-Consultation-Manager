import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;


public class Person implements Serializable {

    private String name;
    private String surname;
    private LocalDate dob;
    private int mobileNum;
    private String nic;

    public Person(String name, String surname, LocalDate dob, int mobileNum, String nic) {
        this.name = name;
        this.surname = surname;
        this.dob = dob;
        this.mobileNum = mobileNum;
        this.nic = nic;
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public int getMobileNum() {
        return mobileNum;
    }

    public String getNic() {
        return nic;
    }



}
