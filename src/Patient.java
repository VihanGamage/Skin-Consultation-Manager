public class Patient extends Person{
    private int PatientId;   //PatientId


    public Patient(int patientId,String name, String surname, String dateOfBirth, int mobileNumber) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.PatientId=patientId;
    }

    //setters & getters
    public int getPatientId() {
        return PatientId;
    }
    public void setPatientId(int patientId) {
        PatientId = patientId;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "PatientId=" + PatientId +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", mobileNumber=" + mobileNumber +
                '}';
    }
}
