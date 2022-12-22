import java.io.Serializable;
import java.util.HashMap;

public class DoctorList implements Serializable {
    private static final long serialVersionUID = 1L;
    private HashMap<Integer,Doctor> DoctorList=new HashMap<>();


    public HashMap<Integer, Doctor> getDoctorList() {
        return DoctorList;
    }

    public void addDoctor(int mLicense , Doctor doctor){
        if(this.DoctorList.size()<=10){
            this.DoctorList.put(mLicense,doctor);
        }
        else {
            System.out.println("Doctor list is full.");
        }
    }

    public void setDoctorList(HashMap<Integer, Doctor> doctorList) {
        DoctorList = doctorList;
    }

    @Override
    public String toString() {
        return "DoctorList{" +
                "DoctorList=" + DoctorList +
                '}';
    }

    public void removeDoctor(int mLicense){

        this.DoctorList.remove(mLicense);
    }
}
