import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;

public class Doctor extends Person implements Serializable {

    private static final long serialVersionUID = 1L;
    private int mLicense;
    private String speciali;

    private ArrayList<Consultation> consultation=new ArrayList();


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
        return "mLicense : " + mLicense +
                "\nSpecialization : " + speciali +
                "\nName : " + getName()+ " " + getSurname() +
                "\nMobile No : " + getMobileNum();
    }

    //    ------------- Custom Methods ----------------
    public void addConsultation(Consultation consultation){
        this.consultation.add(consultation);
    };

    public ArrayList<Consultation> getConsultation() {
        return consultation;
    }

    public boolean checkAvailability(LocalDate appointmentDate, LocalTime startTime, LocalTime endTime) {
        for (Consultation consultation : this.consultation) {
            if (consultation.getDate().equals(appointmentDate)) {
                if (consultation.getStartTime().isBefore(startTime) && consultation.getEndTime().isAfter(startTime)) {
                    return false;
                } else if (consultation.getStartTime().isBefore(endTime) && consultation.getEndTime().isAfter(endTime)) {
                    return false;
                } else if (consultation.getStartTime().isAfter(startTime) && consultation.getEndTime().isBefore(endTime)) {
                    return false;
                } else if(consultation.getStartTime().equals(startTime) || consultation.getEndTime().equals(endTime)){
                    return false;
                }
            }
        }
        return true;
    }

    public void removeConsultation(Consultation consultation) {
        this.consultation.remove(consultation);
    }
}
