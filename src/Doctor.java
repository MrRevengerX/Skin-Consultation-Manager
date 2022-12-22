import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;

public class Doctor extends Person{
    private int mLicense;
    private String speciali;

    private HashMap<LocalDate,Consultation> consultation=new HashMap<>();


    public Doctor(String name, String surname, LocalDate dob, int mobileNum, String nic, int mLicense, String speciali) {
        super(name, surname, dob, mobileNum, nic);
        this.mLicense = mLicense;
        this.speciali = speciali;
    }

    public int getmLicense() {
        return mLicense;
    }

    public void setmLicense(int mLicense) {
        this.mLicense = mLicense;
    }

    public String getSpeciali() {
        return speciali;
    }

    public void setSpeciali(String speciali) {
        this.speciali = speciali;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "mLicense=" + mLicense +
                ", speciali='" + speciali + '\'' +
                ", consultation=" + consultation +
                '}';
    }

    //    ------------- Custom Methods ----------------
    public void addConsultation(LocalDate date,Consultation consultation){
        this.consultation.put(date,consultation);
    };
}
