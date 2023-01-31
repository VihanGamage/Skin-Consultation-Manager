import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Comparator;

import static org.junit.jupiter.api.Assertions.*;

class WestminsterSkinConsultationManagerTest {

    @org.junit.jupiter.api.Test
    void addNewDoctor() {      //testing the doctor added to the arraylist by given variables
        String name="vihan";
        String surname="gamage";
        String dob="11/12/2022";
        int mobileNumber=701234563;
        int licenceNumber =1234;
        String specialisation="cosmetic dermatology";
        Doctor doctor=new Doctor(name,surname,dob,mobileNumber,licenceNumber,specialisation);
        assertTrue(true, String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.add(doctor)));
    }

    @org.junit.jupiter.api.Test
    void deleteDoctor() {    //testing that remove the doctor from arraylist by given licenceNumberReal
        int licenceNumberReal=345;
        for (int i = 0; i < WestminsterSkinConsultationManager.doctorArrayList.size(); i++) {
            if (WestminsterSkinConsultationManager.doctorArrayList.get(i).getLicenceNumber() == licenceNumberReal) {
                WestminsterSkinConsultationManager.doctorArrayList.remove(i);
                assertTrue(true, String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.remove(i)));
            }
        }
    }

    @org.junit.jupiter.api.Test
    void printList() {    //testing arraylist objects can cast to string and print
        WestminsterSkinConsultationManager.doctorArrayList.sort(Comparator.comparing(Doctor::getSurname));
        WestminsterSkinConsultationManager.doctorArrayList.forEach(System.out::println);
    }

    @org.junit.jupiter.api.Test
    void saveFile() throws FileNotFoundException {    //testing arraylist objects can cast to string and write in a text file
        PrintWriter out=new PrintWriter("storedDataTest.txt");
        for (int i=0; i<WestminsterSkinConsultationManager.doctorArrayList.size(); i++){
            out.println(WestminsterSkinConsultationManager.doctorArrayList.get(i));
            assertTrue(true,String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i)));
        }
        out.close();
    }
}