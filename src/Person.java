import java.time.LocalDate;
import java.time.LocalDateTime;


public class Person {
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

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
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

    public void setMobileNum(int mobileNum) {
        this.mobileNum = mobileNum;
    }

    public String getNic() {
        return nic;
    }

    public void setNic(String nic) {
        this.nic = nic;
    }
}
