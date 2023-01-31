import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class WestminsterSkinConsultationManager implements SkinConsultationManager{   //implementing interface
    static Scanner input=new Scanner(System.in);
    protected static ArrayList<Doctor> doctorArrayList=new ArrayList<>();   //creating docotrs arraylist

    public static void main(String[] args) throws FileNotFoundException {
        load();   //loading data from saved text file
        WestminsterSkinConsultationManager westminsterSkinConsultationManager=new WestminsterSkinConsultationManager();
        while (true){       //menu
            System.out.println("""
                    Enter 'A' -> Add a new doctor
                    Enter 'D' -> Delete a doctor
                    Enter 'P' -> Print the list of the doctors
                    Enter 'S' -> Save in a file
                    Enter 'G' -> Open GUI Application
                    Enter 'Q' -> Quit
                    """);
            String response = input.next();
            if (response.equalsIgnoreCase("Q")) {
                break;
            }
            switch (response.toUpperCase(Locale.ROOT)) {
                case "A" -> westminsterSkinConsultationManager.AddNewDoctor();
                case "D" -> westminsterSkinConsultationManager.DeleteDoctor();
                case "P" -> westminsterSkinConsultationManager.PrintList();
                case "S" -> westminsterSkinConsultationManager.SaveFile();
                case "G" -> westminsterSkinConsultationManager.guiMenu();
                default -> System.out.println("Invalid input");
            }
            System.out.println();
        }
    }

    public void guiMenu() throws FileNotFoundException {    //GUI of consultation
        Consultation consultation=new Consultation();
    }

    @Override
    public void AddNewDoctor() {
        if (doctorArrayList.size()>=10){
            System.out.println("You can't add more than 10 doctors");
        }else {
            try {    //try catch method
                System.out.println("Enter name: ");
                String name = input.next();
                System.out.println("Enter surname: ");
                String surname = input.next();
                System.out.println("Enter dateOfBirth: ");
                String dateOfBirth = input.next();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   //date validating
                Date dateCheck=null;
                try {
                    dateCheck = dateFormat.parse(dateOfBirth);
                } catch (ParseException e) {
                    System.out.println("Invalid Date Input");
                }
                System.out.println("Enter mobileNumber: ");
                int mobileNumber = input.nextInt();
                System.out.println("Enter licenceNumber: ");
                int licenceNumber = input.nextInt();
                System.out.println("Enter specialisation: ");
                String specialisation = input.next();
                Doctor doctor = new Doctor(name, surname, dateOfBirth, mobileNumber, licenceNumber, specialisation);
                doctorArrayList.add(doctor);   //adding doctor
            }catch (Exception e){
                System.out.println("Invalid input");
            }
        }
    }

    @Override
    public void DeleteDoctor() {
        int count = 0;
        System.out.println("Enter licenceNumber: ");
        int licenceNumber = input.nextInt();
        for (int i = 0; i < doctorArrayList.size(); i++) {
            if (doctorArrayList.get(i).getLicenceNumber() == licenceNumber) {
                System.out.println(doctorArrayList.get(i) + " is removed");
                doctorArrayList.remove(i);          //deleting doctor
            } else {
                count++;
                continue;
            }
        }
        if (count == doctorArrayList.size()) {    //printing the total number of doctors
            System.out.println("Invalid licence Number");
        }
        System.out.println("Total number of doctors are " + doctorArrayList.size());
    }

    @Override
    public void PrintList() {         //print doctors alphabetically
        Collections.sort(doctorArrayList,Comparator.comparing(Doctor::getSurname));
        doctorArrayList.forEach(System.out::println);

    }

    @Override        //saving to the text file
    public void SaveFile() throws FileNotFoundException {
        PrintWriter out=new PrintWriter("stored_data.txt");
        for (int i=0; i<doctorArrayList.size(); i++){
            out.println(doctorArrayList.get(i));
        }
        out.close();
    }

    public static void load() throws FileNotFoundException{      //loading data from text file
        try {
            File file = new File("C:\\Users\\user\\Desktop\\cwCode\\stored_data.txt");
            Scanner data = new Scanner(file);
            int startIndex;
            int endIndex;
            String equalSign = "=";
            String comma = ",";
            String brackets = "}";
            while (data.hasNextLine()) {
                String line = data.nextLine();
                startIndex = line.indexOf(equalSign);
                endIndex = line.indexOf(comma);
                String licenceNumber = line.substring(startIndex + 1, endIndex);
                int licenceNumberConverted = Integer.parseInt(licenceNumber);
                startIndex = endIndex + 18;
                endIndex = line.indexOf(comma, startIndex);
                String specialisation = line.substring(startIndex, endIndex - 1);
                startIndex = endIndex + 8;
                endIndex = line.indexOf(comma, startIndex);
                String name = line.substring(startIndex, endIndex - 1);
                startIndex = endIndex + 11;
                endIndex = line.indexOf(comma, startIndex);
                String surname = line.substring(startIndex, endIndex - 1);
                startIndex = endIndex + 14;
                endIndex = line.indexOf(comma, startIndex);
                String dateOfBirth = line.substring(startIndex, endIndex);
                startIndex = endIndex + 15;
                endIndex = line.indexOf(brackets);
                String mobileNumber = line.substring(startIndex, endIndex - 1);
                int mobileNumberConverted = Integer.parseInt(mobileNumber);
                Doctor doctor = new Doctor(name, surname, dateOfBirth, mobileNumberConverted, licenceNumberConverted, specialisation);
                doctorArrayList.add(doctor);
            }
        }catch (IOException ioe){
            System.out.println("No stored doctors details");
        }
    }
}


