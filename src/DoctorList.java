import java.io.Serializable;
import java.util.HashMap;

public class DoctorList implements Serializable {
    private HashMap<Integer,Doctor> doctorList =new HashMap<>();


    public HashMap<Integer, Doctor> getDoctorList() {
        return doctorList;
    }

    public void addDoctor(int mLicense , Doctor doctor){
        if(this.doctorList.size()<=10){
            this.doctorList.put(mLicense,doctor);
        }
        else {
            System.out.println("Doctor list is full.");
        }
    }

    public void setDoctorList(HashMap<Integer, Doctor> doctorList) {
        this.doctorList = doctorList;
    }

    @Override
    public String toString() {
        return "DoctorList{" +
                "DoctorList=" + doctorList +
                '}';
    }

    public void removeDoctor(int mLicense){

        this.doctorList.remove(mLicense);
    }
}
