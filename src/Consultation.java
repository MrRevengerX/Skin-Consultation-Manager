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

    private String image = null;





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

    public LocalTime getStartTime() {
        return startTime;
    }

    public LocalDate getDate() {
        return date;
    }

    public LocalTime getEndTime() {
        return endTime;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public Patient getPatient() {
        return patient;
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

    public void setImage(String path) {
        this.image = path;
    }


    public String getImage() {
        return image;
    }

}
