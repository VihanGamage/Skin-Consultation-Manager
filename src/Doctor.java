public class Doctor extends Person{
    private int licenceNumber;   //doctor licenceNumber
    private String specialisation;   //doctor specialisation

    public Doctor(String name, String surname, String dateOfBirth, int mobileNumber,int licenceNumber,String specialisation) {
        super(name, surname, dateOfBirth, mobileNumber);
        this.licenceNumber=licenceNumber;
        this.specialisation=specialisation;
    }

    //setters & getters
    public int getLicenceNumber() {
        return licenceNumber;
    }
    public void setLicenceNumber(int licenceNumber) {
        this.licenceNumber = licenceNumber;
    }
    public String getSpecialisation() {
        return specialisation;
    }
    public void setSpecialisation(String specialisation) {
        this.specialisation = specialisation;
    }

    @Override
    public String toString() {
        return "Doctor { " +
                "licenceNumber=" + licenceNumber +
                ", specialisation='" + specialisation + '\'' +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", dateOfBirth=" + dateOfBirth +
                ", mobileNumber=" + mobileNumber + " }";
    }
}
