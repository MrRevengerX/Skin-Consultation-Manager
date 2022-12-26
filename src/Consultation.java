import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.UUID;

public class Consultation implements Serializable {

    private static final long serialVersionUID = 1L;

    private String consultationID;

    private LocalTime startTime;
    private LocalTime endTime;

    private LocalDate date;
    private int cost;
    private Patient patient;

    private String additionalNote;

    private Doctor assignedDoctor;




    public Consultation(LocalDate date, LocalTime startTime, LocalTime endTime, int cost, Patient patient) {
        this.consultationID = String.valueOf(UUID.randomUUID());
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.patient = patient;
    }

    public String getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(String consultationID) {
        this.consultationID = consultationID;
    }

    public LocalTime getStartTime() {
        return startTime;
    }

    public void setTime(LocalTime startTime) {
        this.startTime = startTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalTime endTime) {
        this.endTime = endTime;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    public String getAdditionalNote() {
        return additionalNote;
    }

    public void setAdditionalNote(String additionalNote) {
        this.additionalNote = additionalNote;
    }

    public Doctor getAssignedDoctor() {
        return assignedDoctor;
    }

    public void setAssignedDoctor(Doctor assignedDoctor) {
        this.assignedDoctor = assignedDoctor;
    }

    @Override
    public String toString() {
        return "Consultation ID : " + consultationID +
                "\nDate : " + date +
                "\nStart Time : " + startTime +
                "\nEnd Time : " + endTime +
                "\nCost : $"+ cost;
    }
}
