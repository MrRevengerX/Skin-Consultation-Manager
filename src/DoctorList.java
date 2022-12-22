import java.util.HashMap;

public class DoctorList {
    private HashMap<Integer,Doctor> DoctorList=new HashMap<>();




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
}
