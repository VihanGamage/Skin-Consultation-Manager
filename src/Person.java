public abstract class Person {
    protected String name;   //person name
    protected String surname;   //person surname
    protected String dateOfBirth;   //person dateOfBirth
    protected int mobileNumber;   //person mobileNumber

    public Person(String name, String surname, String dateOfBirth, int mobileNumber) {
        this.name = name;
        this.surname = surname;
        this.dateOfBirth = dateOfBirth;
        this.mobileNumber = mobileNumber;
    }

    //setters & getters
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public String getDateOfBirth() {
        return dateOfBirth;
    }
    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }
    public int getMobileNumber() {
        return mobileNumber;
    }
    public void setMobileNumber(int mobileNumber) {
        this.mobileNumber = mobileNumber;
    }
}
