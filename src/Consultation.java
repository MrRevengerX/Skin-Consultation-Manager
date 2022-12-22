import java.time.LocalDateTime;

public class Consultation {
    private int consultationID;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private int cost;
    private Patient patient;

    public Consultation(int consultationID, LocalDateTime startTime, LocalDateTime endTime, int cost, Patient patient) {
        this.consultationID = consultationID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.cost = cost;
        this.patient = patient;
    }

    public int getConsultationID() {
        return consultationID;
    }

    public void setConsultationID(int consultationID) {
        this.consultationID = consultationID;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
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
}
