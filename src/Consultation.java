import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import static javax.swing.JOptionPane.INFORMATION_MESSAGE;

public class Consultation extends JFrame {
    private Doctor doctor;   //consultation Doctor
    private Patient patient;   //consultation patient
    private String date;   //consultation date
    private String time;   //consultation time
    private String cost;   //consultation cost
    private String notes;   //consultation notes

    /*-------- UI Components ---------*/
    private JRadioButton btn1;
    private JRadioButton btn2;
    private JRadioButton btn3;
    private JRadioButton btn4;
    private JRadioButton btn5;
    private JRadioButton btn6;
    private JRadioButton btn7;
    private JRadioButton btn8;
    private JRadioButton btn9;
    private JRadioButton btn10;
    private JRadioButton btn11;
    private JRadioButton btn12;
    private JButton submit;
    private JTextField patientIdField;
    private JTextField patientNameField;
    private JTextField patientSurnameField;
    private JTextField dobField;
    private JTextField mobileNumberField;
    private JTextField notesField;
    private JComboBox<String> jComboBox;

    protected static String[] timeSlot = new String[1];    //radio buttons array
    protected static String[] dateSlot = new String[1];    //radio buttons array
    protected static String[] costSlot = new String[1];    //radio buttons array
    protected static String docName;  //comboBox inputs
    ImageIcon logo=new ImageIcon("src/logo.png");    //Logo Image

    protected static ArrayList<Consultation> consultationArrayList=new ArrayList<>();   //consultation ArrayList

    public Consultation(Doctor doctor, Patient patient, String date, String time, String cost, String notes) {
        this.doctor=doctor;
        this.patient=patient;
        this.date = date;
        this.time = time;
        this.cost = cost;
        this.notes = notes;
    }

    Consultation() throws FileNotFoundException {
        loadConsultation();      //loading consultation arraylist
        JTabbedPane jTabbedPane=new JTabbedPane(JTabbedPane.TOP);
        jTabbedPane.add("View Doctors",viewDoctors());
        jTabbedPane.add("Add Consultations",addConsultations());
        jTabbedPane.add("View Consultations",viewConsultations());
        jTabbedPane.setBackground(Color.magenta);
        getContentPane().add(jTabbedPane);
        this.add(jTabbedPane);
        this.setTitle("Consultation Menu");
        this.setSize(1300, 700);
        this.setResizable(false);
        this.setAlwaysOnTop(true);
        this.setVisible(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setIconImage(logo.getImage());
    }

    public JPanel viewDoctors(){      //view doctors by table
        JPanel jPanel1=new JPanel(new BorderLayout());
        JPanel jPanel2=new JPanel(new BorderLayout());
        String[] columnNames = {"Name", "Surname", "Date Of Birth","Mobile Number", "Licence Number", "Specialisation"};
        DefaultTableModel model=new DefaultTableModel(columnNames,0);     //creating JTable
        JTable jTable=new JTable(model);
        DefaultTableCellRenderer defaultTableCellRenderer = new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(76);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(76);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(91);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(92);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(86);
        jTable.setRowHeight(23);
        String[] rowData = new String[6];
        for (int i=0; i<WestminsterSkinConsultationManager.doctorArrayList.size(); i++){
            rowData[0]=WestminsterSkinConsultationManager.doctorArrayList.get(i).getName();
            rowData[1]=WestminsterSkinConsultationManager.doctorArrayList.get(i).getSurname();
            rowData[2]= String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i).getDateOfBirth());
            rowData[3]= String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i).getMobileNumber());
            rowData[4]= String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i).getLicenceNumber());
            rowData[5]=WestminsterSkinConsultationManager.doctorArrayList.get(i).getSpecialisation();
            model.addRow(rowData);
        }
        jTable.setCellSelectionEnabled(false);
        jTable.setColumnSelectionAllowed(false);
        jTable.setRowSelectionAllowed(true);
        jTable.setBackground(Color.green);
        jTable.getTableHeader().setBackground(Color.CYAN);
        jTable.getTableHeader().setForeground(Color.RED);
        jTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
        jTable.setGridColor(Color.magenta);
        jTable.setPreferredScrollableViewportSize(new Dimension(580,550));
        jTable.setDefaultRenderer(Object.class,defaultTableCellRenderer);
        jTable.getTableHeader().setReorderingAllowed(false);
        jTable.getTableHeader().setResizingAllowed(false);
        JScrollPane jScrollPane=new JScrollPane(jTable);
        jPanel2.add(jScrollPane,BorderLayout.NORTH);
        jPanel1.add(jPanel2,BorderLayout.WEST);
        JButton jButton=new JButton("SORT ALPHABETICALLY");    //creating SORT ALPHABETICALLY button
        jButton.setFont(new Font("Comic Sans",Font.BOLD,40));
        jButton.setBackground(Color.pink);
        jPanel2.add(jButton,BorderLayout.CENTER);
        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==jButton) {
                    model.setRowCount(0);    //sort alphabetically
                    WestminsterSkinConsultationManager.doctorArrayList.sort(Comparator.comparing(Doctor::getSurname));
                    String[] rowData = new String[6];
                    for (int i = 0; i < WestminsterSkinConsultationManager.doctorArrayList.size(); i++) {
                        rowData[0] = WestminsterSkinConsultationManager.doctorArrayList.get(i).getName();
                        rowData[1] = WestminsterSkinConsultationManager.doctorArrayList.get(i).getSurname();
                        rowData[2] = String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i).getDateOfBirth());
                        rowData[3] = String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i).getMobileNumber());
                        rowData[4] = String.valueOf(WestminsterSkinConsultationManager.doctorArrayList.get(i).getLicenceNumber());
                        rowData[5] = WestminsterSkinConsultationManager.doctorArrayList.get(i).getSpecialisation();
                        model.addRow(rowData);
                    }
                }
            }
        });
        //showing labels
        JPanel jPanel3=new JPanel(new GridLayout(4,7));
        Border border = BorderFactory.createLineBorder(Color.magenta, 4);
        JLabel monday=new JLabel("MONDAY",SwingConstants.CENTER);
        monday.setFont(new Font("SansSerif", Font.BOLD, 14));
        monday.setBackground(Color.CYAN);
        monday.setOpaque(true);
        monday.setBorder(border);
        jPanel3.add(monday);
        JLabel tuesday=new JLabel("TUESDAY",SwingConstants.CENTER);
        tuesday.setFont(new Font("SansSerif", Font.BOLD, 14));
        tuesday.setBackground(Color.CYAN);
        tuesday.setOpaque(true);
        tuesday.setBorder(border);
        jPanel3.add(tuesday);
        JLabel wednesday=new JLabel("WEDNESDAY",SwingConstants.CENTER);
        wednesday.setFont(new Font("SansSerif", Font.BOLD, 14));
        wednesday.setBackground(Color.CYAN);
        wednesday.setOpaque(true);
        wednesday.setBorder(border);
        jPanel3.add(wednesday);
        JLabel thursday=new JLabel("THURSDAY",SwingConstants.CENTER);
        thursday.setFont(new Font("SansSerif", Font.BOLD, 14));
        thursday.setBackground(Color.CYAN);
        thursday.setOpaque(true);
        thursday.setBorder(border);
        jPanel3.add(thursday);
        JLabel friday=new JLabel("FRIDAY",SwingConstants.CENTER);
        friday.setFont(new Font("SansSerif", Font.BOLD, 14));
        friday.setBackground(Color.CYAN);
        friday.setOpaque(true);
        friday.setBorder(border);
        jPanel3.add(friday);
        JLabel saturday=new JLabel("SATURDAY",SwingConstants.CENTER);
        saturday.setFont(new Font("SansSerif", Font.BOLD, 14));
        saturday.setBackground(Color.CYAN);
        saturday.setOpaque(true);
        saturday.setBorder(border);
        jPanel3.add(saturday);
        JLabel sunday=new JLabel("SUNDAY",SwingConstants.CENTER);
        sunday.setFont(new Font("SansSerif", Font.BOLD, 12));
        sunday.setBackground(Color.CYAN);
        sunday.setOpaque(true);
        sunday.setBorder(border);
        jPanel3.add(sunday);
        //adding to relevant arraylists
        ArrayList<JLabel> fourToFive=new ArrayList<>();
        ArrayList<JLabel> fiveToSix=new ArrayList<>();
        ArrayList<JLabel> sixToSeven=new ArrayList<>();
        for (int i=0; i<7; i++){
            JLabel jLabel=new JLabel("4 pm - 5 pm",SwingConstants.CENTER);
            jLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
            fourToFive.add(jLabel);
            jPanel3.add(jLabel);
        }
        for (int i=0; i<7; i++){
            JLabel jLabel=new JLabel("5 pm - 6 pm",SwingConstants.CENTER);
            jLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
            fiveToSix.add(jLabel);
            jPanel3.add(jLabel);
        }
        for (int i=0; i<7; i++){
            JLabel jLabel=new JLabel("6 pm - 7 pm",SwingConstants.CENTER);
            jLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
            sixToSeven.add(jLabel);
            jPanel3.add(jLabel);
        }
        jPanel1.add(jPanel3,BorderLayout.CENTER);
        jTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                JTable target=(JTable)e.getSource();
                int row=target.getSelectedRow();
                String docName= (String) model.getValueAt(row,0);
                for (int k=0; k<7; k++){
                    fourToFive.get(k).setBackground(Color.green);
                    fourToFive.get(k).setOpaque(true);
                    fourToFive.get(k).setBorder(border);
                }
                for (int k=0; k<7; k++){
                    fiveToSix.get(k).setBackground(Color.green);
                    fiveToSix.get(k).setOpaque(true);
                    fiveToSix.get(k).setBorder(border);

                }
                for (int k=0; k<7; k++){
                    sixToSeven.get(k).setBackground(Color.green);
                    sixToSeven.get(k).setOpaque(true);
                    sixToSeven.get(k).setBorder(border);
                }
                //showing available times by labels [GREEN FOR AVAILABLE , RED FOR BOOKED]
                for (int i=0; i<consultationArrayList.size(); i++){
                    if (consultationArrayList.get(i).doctor.getName().equals(docName)){
                        if (consultationArrayList.get(i).getTime().equals("4 - 5")){
                            if (consultationArrayList.get(i).getDate().equals("monday")){
                                fourToFive.get(0).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("tuesday")){
                                fourToFive.get(1).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("wednesday")){
                                fourToFive.get(2).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("thursday")){
                                fourToFive.get(3).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("friday")){
                                fourToFive.get(4).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("saturday")){
                                fourToFive.get(5).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("sunday")){
                                fourToFive.get(6).setBackground(Color.red);
                            }
                        }
                        if (consultationArrayList.get(i).getTime().equals("5 - 6")) {
                            if (consultationArrayList.get(i).getDate().equals("monday")) {
                                fiveToSix.get(0).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("tuesday")) {
                                fiveToSix.get(1).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("wednesday")) {
                                fiveToSix.get(2).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("thursday")) {
                                fiveToSix.get(3).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("friday")) {
                                fiveToSix.get(4).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("saturday")) {
                                fiveToSix.get(5).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("sunday")) {
                                fiveToSix.get(6).setBackground(Color.red);
                            }
                        }
                        if (consultationArrayList.get(i).getTime().equals("6 - 7")) {
                            if (consultationArrayList.get(i).getDate().equals("monday")) {
                                sixToSeven.get(0).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("tuesday")) {
                                sixToSeven.get(1).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("wednesday")) {
                                sixToSeven.get(2).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("thursday")) {
                                sixToSeven.get(3).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("friday")) {
                                sixToSeven.get(4).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("saturday")) {
                                sixToSeven.get(5).setBackground(Color.red);
                            }
                            if (consultationArrayList.get(i).getDate().equals("sunday")) {
                                sixToSeven.get(6).setBackground(Color.red);
                            }
                        }
                    }
                }
            }
        });
        return jPanel1;
    }

    public JPanel addConsultations() {     //adding consultation to arraylist
        JPanel jPanel1=new JPanel(new BorderLayout());
        JPanel jPanel=new JPanel(new GridLayout(10,2));
        Border border1 = BorderFactory.createLineBorder(Color.red, 3);
        Border border2 = BorderFactory.createLineBorder(Color.BLUE, 3);
        JLabel doctorName=new JLabel("Enter Doctor Name");
        doctorName.setFont(new Font("SansSerif", Font.BOLD, 14));
        doctorName.setPreferredSize(new Dimension(150,51));
        doctorName.setBackground(Color.pink);
        doctorName.setOpaque(true);
        doctorName.setBorder(border1);
        String[] docNames = new String[10];
        for (int i = 0; i < WestminsterSkinConsultationManager.doctorArrayList.size(); i++) {
             docNames[i]= WestminsterSkinConsultationManager.doctorArrayList.get(i).getName();
        }
        jComboBox=new JComboBox<String>(docNames);
        jComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==jComboBox){
                    docName= Objects.requireNonNull(jComboBox.getSelectedItem()).toString();
                }
            }
        });
        jComboBox.setFont(new Font("SansSerif", Font.BOLD, 14));
        jComboBox.setBorder(border2);
        jComboBox.setBackground(Color.LIGHT_GRAY);
        JLabel patientId=new JLabel("Enter Patient ID");
        patientId.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientId.setBackground(Color.pink);
        patientId.setOpaque(true);
        patientId.setBorder(border1);
        patientIdField=new JTextField();
        patientIdField.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientIdField.setBorder(border2);
        patientIdField.setBackground(Color.LIGHT_GRAY);
        JLabel patientName=new JLabel("Enter Patient Name");
        patientName.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientName.setBackground(Color.pink);
        patientName.setOpaque(true);
        patientName.setBorder(border1);
        patientNameField=new JTextField();
        patientNameField.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientNameField.setBorder(border2);
        patientNameField.setBackground(Color.LIGHT_GRAY);
        JLabel patientSurname=new JLabel("Enter Patient Surname");
        patientSurname.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientSurname.setBackground(Color.pink);
        patientSurname.setOpaque(true);
        patientSurname.setBorder(border1);
        patientSurnameField =new JTextField();
        patientSurnameField.setFont(new Font("SansSerif", Font.BOLD, 14));
        patientSurnameField.setBorder(border2);
        patientSurnameField.setBackground(Color.LIGHT_GRAY);
        JLabel dateOfBirth=new JLabel("Enter Patient Date Of Birth [dd/MM/yyyy]");
        dateOfBirth.setFont(new Font("SansSerif", Font.BOLD, 14));
        dateOfBirth.setBackground(Color.pink);
        dateOfBirth.setOpaque(true);
        dateOfBirth.setBorder(border1);
        dobField =new JTextField();
        dobField.setFont(new Font("SansSerif", Font.BOLD, 14));
        dobField.setBorder(border2);
        dobField.setBackground(Color.LIGHT_GRAY);
        JLabel mobileNumber=new JLabel("Enter Patient Mobile Number");
        mobileNumber.setFont(new Font("SansSerif", Font.BOLD, 14));
        mobileNumber.setBackground(Color.pink);
        mobileNumber.setOpaque(true);
        mobileNumber.setBorder(border1);
        mobileNumberField=new JTextField();
        mobileNumberField.setFont(new Font("SansSerif", Font.BOLD, 14));
        mobileNumberField.setBorder(border2);
        mobileNumberField.setBackground(Color.LIGHT_GRAY);
        JLabel date=new JLabel("Enter The Day");
        date.setFont(new Font("SansSerif", Font.BOLD, 14));
        date.setBackground(Color.pink);
        date.setOpaque(true);
        date.setBorder(border1);
        JPanel inner1=new JPanel(new GridLayout(1,7));
        inner1.setBorder(border2);
        btn1=new JRadioButton("MONDAY");
        btn2=new JRadioButton("TUESDAY");
        btn3=new JRadioButton("WEDNESDAY");
        btn4=new JRadioButton("THURSDAY");
        btn5=new JRadioButton("FRIDAY");
        btn6=new JRadioButton("SATURDAY");
        btn7=new JRadioButton("SUNDAY");
        btn1.setBackground(Color.lightGray);
        btn2.setBackground(Color.lightGray);
        btn3.setBackground(Color.lightGray);
        btn4.setBackground(Color.lightGray);
        btn5.setBackground(Color.lightGray);
        btn6.setBackground(Color.lightGray);
        btn7.setBackground(Color.lightGray);
        ButtonGroup group1=new ButtonGroup();
        group1.add(btn1);
        group1.add(btn2);
        group1.add(btn3);
        group1.add(btn4);
        group1.add(btn5);
        group1.add(btn6);
        group1.add(btn7);
        inner1.add(btn1);
        inner1.add(btn2);
        inner1.add(btn3);
        inner1.add(btn4);
        inner1.add(btn5);
        inner1.add(btn6);
        inner1.add(btn7);
        JLabel time=new JLabel("Enter The Time");
        time.setFont(new Font("SansSerif", Font.BOLD, 14));
        time.setBackground(Color.pink);
        time.setOpaque(true);
        time.setBorder(border1);
        JPanel inner2=new JPanel(new GridLayout(1,3));
        inner2.setBorder(border2);
        btn8=new JRadioButton("4 pm - 5 pm");
        btn8.setFont(new Font("SansSerif", Font.BOLD, 17));
        btn9=new JRadioButton("5 pm - 6 pm");
        btn9.setFont(new Font("SansSerif", Font.BOLD, 17));
        btn10=new JRadioButton("6 pm - 7 pm");
        btn10.setFont(new Font("SansSerif", Font.BOLD, 17));
        btn8.setBackground(Color.lightGray);
        btn9.setBackground(Color.lightGray);
        btn10.setBackground(Color.lightGray);
        ButtonGroup group2=new ButtonGroup();
        group2.add(btn8);
        group2.add(btn9);
        group2.add(btn10);
        inner2.add(btn8);
        inner2.add(btn9);
        inner2.add(btn10);
        JLabel cost=new JLabel("Enter The Cost");
        cost.setFont(new Font("SansSerif", Font.BOLD, 14));
        cost.setBackground(Color.pink);
        cost.setOpaque(true);
        cost.setBorder(border1);
        JPanel inner3=new JPanel(new GridLayout(1,1));
        inner3.setBorder(border2);
        btn11=new JRadioButton("£ 15");
        btn11.setFont(new Font("SansSerif", Font.BOLD, 17));
        btn12=new JRadioButton("£ 25");
        btn12.setFont(new Font("SansSerif", Font.BOLD, 17));
        btn11.setBackground(Color.lightGray);
        btn12.setBackground(Color.lightGray);
        ButtonGroup group3=new ButtonGroup();
        group3.add(btn11);
        group3.add(btn12);
        inner3.add(btn11);
        inner3.add(btn12);
        JLabel notes=new JLabel("Enter Patients Notes [This Will Be Encrypted]");
        notes.setFont(new Font("SansSerif", Font.BOLD, 14));
        notes.setBackground(Color.pink);
        notes.setOpaque(true);
        notes.setBorder(border1);
        notesField=new JTextField();
        notesField.setFont(new Font("SansSerif", Font.BOLD, 14));
        notesField.setBorder(border2);
        notesField.setBackground(Color.LIGHT_GRAY);
        jPanel.add(doctorName);
        jPanel.add(jComboBox);
        jPanel.add(patientId);
        jPanel.add(patientIdField);
        jPanel.add(patientName);
        jPanel.add(patientNameField);
        jPanel.add(patientSurname);
        jPanel.add(patientSurnameField);
        jPanel.add(dateOfBirth);
        jPanel.add(dobField);
        jPanel.add(mobileNumber);
        jPanel.add(mobileNumberField);
        jPanel.add(date);
        jPanel.add(inner1);
        jPanel.add(time);
        jPanel.add(inner2);
        jPanel.add(cost);
        jPanel.add(inner3);
        jPanel.add(notes);
        jPanel.add(notesField);
        jPanel1.add(jPanel,BorderLayout.NORTH);
        submit=new JButton("SUBMIT");
        submit.setFont(new Font("Comic Sans",Font.BOLD,40));
        submit.setBackground(Color.green);
        submit.setBorder(border1);
        jPanel1.add(submit,BorderLayout.SOUTH);
        btn1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn1){
                    dateSlot[0] ="monday";
                }
            }
        });
        btn2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn2){
                    dateSlot[0] ="tuesday";
                }
            }
        });
        btn3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn3){
                    dateSlot[0] ="wednesday";
                }
            }
        });
        btn4.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn4){
                    dateSlot[0] ="thursday";
                }
            }
        });
        btn5.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn5){
                    dateSlot[0] ="friday";
                }
            }
        });
        btn6.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn6){
                    dateSlot[0] ="saturday";
                }
            }
        });
        btn7.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn7){
                    dateSlot[0] ="sunday";
                }
            }
        });
        btn8.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn8){
                    timeSlot[0] ="4 - 5";
                }
            }
        });
        btn9.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn9){
                    timeSlot[0] ="5 - 6";
                }
            }
        });
        btn10.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn10){
                    timeSlot[0] ="6 - 7";
                }
            }
        });
        btn11.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn11){
                    costSlot[0] ="15";
                }
            }
        });
        btn12.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (e.getSource()==btn12){
                    costSlot[0] ="25";
                }
            }
        });
        submit.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e){
                if (e.getSource()==submit) {
                    Doctor doctor1 =null;
                    for (int i = 0; i < WestminsterSkinConsultationManager.doctorArrayList.size(); i++) {
                        String nameInput = WestminsterSkinConsultationManager.doctorArrayList.get(i).getName();
                        if (nameInput.equals(docName)) {
                            doctor1 = WestminsterSkinConsultationManager.doctorArrayList.get(i);
                        }
                    }
                    int freeConsultationCount = 0;
                    int totalConsultationCount=0;

                    //total Consultation Count of selected doctor
                    for (Consultation item : consultationArrayList) {
                        if (item.doctor == doctor1) {
                            totalConsultationCount++;
                        }
                    }
                    //free consultation count of selected doctor
                    for (Consultation value : consultationArrayList) {
                        if (value.doctor == doctor1) {
                            if (!((value.getDate().equals(dateSlot[0])) && (value.getTime().equals(timeSlot[0])))) {
                                freeConsultationCount++;
                            }
                        }
                    }

                    //cost amount(15/25) checking by patient ID
                    for (Consultation value : consultationArrayList) {
                        if (value.patient.getPatientId() == Integer.parseInt(patientIdField.getText())) {
                            costSlot[0] = "25";
                        }
                    }

                    //encrypting patients notes
                    String patientNotes=notesField.getText();
                    byte[] encrypted = Base64.getEncoder().encode(patientNotes.getBytes());
                    String encryptedNotes=new String(encrypted);

                    //adding the consultation to the arraylist
                    if (totalConsultationCount == 0) {   //new doctor
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   //validating the date
                        Date dateCheck=null;
                        try {
                            dateCheck = dateFormat.parse(dobField.getText());
                        } catch (ParseException exp) {
                            invalidDate();
                        }
                        Patient patient1 = new Patient(Integer.parseInt(patientIdField.getText()), patientNameField.getText(), patientSurnameField.getText(),dobField.getText(), Integer.parseInt(mobileNumberField.getText()));
                        Consultation consultation = new Consultation(doctor1, patient1, dateSlot[0], timeSlot[0], costSlot[0], encryptedNotes);
                        consultationArrayList.add(consultation);
                        try {
                            saveConsultationFile();
                        } catch (FileNotFoundException er) {
                            throw new RuntimeException(er);
                        }
                    } else if (totalConsultationCount == freeConsultationCount) {    //exiting doctor
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");   //validating the date
                        Date dateCheck=null;
                        try {
                            dateCheck = dateFormat.parse(dobField.getText());
                        } catch (ParseException exp) {
                            invalidDate();
                        }
                        Patient patient1 = new Patient(Integer.parseInt(patientIdField.getText()), patientNameField.getText(), patientSurnameField.getText(), dobField.getText(), Integer.parseInt(mobileNumberField.getText()));
                        Consultation consultation = new Consultation(doctor1, patient1, dateSlot[0], timeSlot[0], costSlot[0], encryptedNotes);
                        consultationArrayList.add(consultation);
                        try {
                            saveConsultationFile();
                        } catch (FileNotFoundException er) {
                            throw new RuntimeException(er);
                        }
                    } else {       //random doctor
                        Doctor randomDoctor;        //generating a random doctor
                        Random random = new Random();     //adding to the random doctor
                        int randomNum = random.nextInt(WestminsterSkinConsultationManager.doctorArrayList.size());
                        randomDoctor = WestminsterSkinConsultationManager.doctorArrayList.get(randomNum);
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");    //validating the date
                        Date dateCheck=null;
                        try {
                            dateCheck = dateFormat.parse(dobField.getText());
                        } catch (ParseException exp) {
                            invalidDate();
                        }
                        Patient patient1 = new Patient(Integer.parseInt(patientIdField.getText()), patientNameField.getText(), patientSurnameField.getText(), dobField.getText(), Integer.parseInt(mobileNumberField.getText()));
                        Consultation consultation = new Consultation(randomDoctor, patient1, dateSlot[0], timeSlot[0], costSlot[0], encryptedNotes);
                        consultationArrayList.add(consultation);
                        try {
                            saveConsultationFile();
                        } catch (FileNotFoundException er) {
                            throw new RuntimeException(er);
                        }
                    }
                }
            }
        });
        return jPanel1;
    }

    //saving the data to text file
    public void saveConsultationFile() throws FileNotFoundException {
        PrintWriter out=new PrintWriter("stored_consultation_data.txt");
        for (Consultation consultation : consultationArrayList) {
            out.println(consultation);
        }
        out.close();
    }
    //loading data from the text file
    public static void loadConsultation() throws FileNotFoundException{
        try {
            File file = new File("C:\\Users\\user\\Desktop\\cwCode\\stored_consultation_data.txt");
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
                String licenceNumber = line.substring(startIndex + 24, endIndex);
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
                startIndex = endIndex + 14;
                endIndex = line.indexOf(brackets);
                String mobileNumber = line.substring(startIndex+1, endIndex-1 );
                int mobileNumberConverted = Integer.parseInt(mobileNumber);
                Doctor doctor1=new Doctor(name,surname,dateOfBirth,mobileNumberConverted,licenceNumberConverted,specialisation);

                startIndex = endIndex + 29;
                endIndex = line.indexOf(comma, startIndex);
                String PatientId = line.substring(startIndex, endIndex);
                int PatientIdConverted = Integer.parseInt(PatientId);
                startIndex = endIndex + 8;
                endIndex = line.indexOf(comma, startIndex);
                String patientName = line.substring(startIndex, endIndex-1);
                startIndex = endIndex + 11;
                endIndex = line.indexOf(comma, startIndex);
                String patientSurname = line.substring(startIndex, endIndex-1);
                startIndex = endIndex + 14;
                endIndex = line.indexOf(comma, startIndex);
                String patientDateOfBirth = line.substring(startIndex, endIndex);
                startIndex = endIndex + 15;
                endIndex = line.indexOf(brackets,startIndex);
                String patientMobileNumber = line.substring(startIndex, endIndex);
                int patientMobileNumberConverted = Integer.parseInt(patientMobileNumber);
                Patient patient1=new Patient(PatientIdConverted,patientName,patientSurname,patientDateOfBirth,patientMobileNumberConverted);

                startIndex = endIndex + 8;
                endIndex = line.indexOf(comma, startIndex);
                String day = line.substring(startIndex, endIndex);
                startIndex = endIndex + 7;
                endIndex = line.indexOf(comma, startIndex);
                String time = line.substring(startIndex, endIndex);
                startIndex = endIndex + 7;
                endIndex = line.indexOf(comma, startIndex);
                String cost = line.substring(startIndex, endIndex);
                startIndex = endIndex + 9;
                endIndex = line.indexOf(brackets, startIndex);
                String notes = line.substring(startIndex, endIndex-1);
                Consultation consultation=new Consultation(doctor1,patient1,day,time,cost,notes);  //creating the consultation
                consultationArrayList.add(consultation);
            }
        }catch (IOException ioe){
            System.out.println("No stored consultation details");
        }
    }
    public JPanel viewConsultations(){     //viewing consultations by table
        JPanel jPanel=new JPanel(new BorderLayout());
        String[] columnNames = {"Doctor Name","Surname","Date Of Birth","Mobile Number","Licence Number","Specialisation","Patient ID","Patient Name","Surname","Date Of Birth","Mobile Number","Date","Time","Cost","Notes"};
        DefaultTableModel model=new DefaultTableModel(columnNames,0);
        JTable jTable=new JTable(model);
        DefaultTableCellRenderer defaultTableCellRenderer=new DefaultTableCellRenderer();
        defaultTableCellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        jTable.getColumnModel().getColumn(0).setPreferredWidth(75);
        jTable.getColumnModel().getColumn(1).setPreferredWidth(72);
        jTable.getColumnModel().getColumn(2).setPreferredWidth(79);
        jTable.getColumnModel().getColumn(3).setPreferredWidth(83);
        jTable.getColumnModel().getColumn(4).setPreferredWidth(90);
        jTable.getColumnModel().getColumn(5).setPreferredWidth(77);
        jTable.getColumnModel().getColumn(6).setPreferredWidth(70);
        jTable.getColumnModel().getColumn(7).setPreferredWidth(77);
        jTable.getColumnModel().getColumn(8).setPreferredWidth(72);
        jTable.getColumnModel().getColumn(9).setPreferredWidth(76);
        jTable.getColumnModel().getColumn(10).setPreferredWidth(84);
        jTable.getColumnModel().getColumn(11).setPreferredWidth(72);
        jTable.getColumnModel().getColumn(12).setPreferredWidth(55);
        jTable.getColumnModel().getColumn(13).setPreferredWidth(55);
        jTable.getColumnModel().getColumn(14).setPreferredWidth(85);
        jTable.setRowHeight(23);
        String[] rowData = new String[15];
        for (int i=0; i<consultationArrayList.size(); i++){    //getting row data from consultation arraylist
            rowData[0]=consultationArrayList.get(i).doctor.getName();
            rowData[1]=consultationArrayList.get(i).doctor.getSurname();
            rowData[2]= String.valueOf(consultationArrayList.get(i).doctor.getDateOfBirth());
            rowData[3]= String.valueOf(consultationArrayList.get(i).doctor.getMobileNumber());
            rowData[4]= String.valueOf(consultationArrayList.get(i).doctor.getLicenceNumber());
            rowData[5]=consultationArrayList.get(i).doctor.getSpecialisation();
            rowData[6]= String.valueOf(consultationArrayList.get(i).patient.getPatientId());
            rowData[7]=consultationArrayList.get(i).patient.getName();
            rowData[8]=consultationArrayList.get(i).patient.getSurname();
            rowData[9]= String.valueOf(consultationArrayList.get(i).patient.getDateOfBirth());
            rowData[10]= String.valueOf(consultationArrayList.get(i).patient.getMobileNumber());
            rowData[11]=consultationArrayList.get(i).getDate();
            rowData[12]=consultationArrayList.get(i).getTime();
            rowData[13]=consultationArrayList.get(i).getCost();
            rowData[14]=consultationArrayList.get(i).getNotes();
            model.addRow(rowData);
            jTable.setCellSelectionEnabled(false);
            jTable.setColumnSelectionAllowed(false);
            jTable.setRowSelectionAllowed(true);
            jTable.setBackground(Color.green);
            jTable.getTableHeader().setBackground(Color.CYAN);
            jTable.getTableHeader().setForeground(Color.RED);
            jTable.getTableHeader().setFont(new Font("SansSerif", Font.BOLD, 13));
            jTable.setGridColor(Color.magenta);
            jTable.setPreferredScrollableViewportSize(new Dimension(1280,550));
            jTable.setEnabled(false);
            jTable.setDefaultRenderer(Object.class,defaultTableCellRenderer);
            jTable.getTableHeader().setReorderingAllowed(false);
            jTable.getTableHeader().setResizingAllowed(false);
            JScrollPane jScrollPane=new JScrollPane(jTable);
            jPanel.add(jScrollPane,BorderLayout.CENTER);
            JButton jButton=new JButton("DECRYPT PATIENTS NOTES");
            jButton.setFont(new Font("Comic Sans",Font.BOLD,40));
            jButton.setBackground(Color.pink);
            jPanel.add(jButton,BorderLayout.SOUTH);
            jButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (e.getSource()==jButton){
                        model.setRowCount(0);
                        String[] rowData = new String[15];
                        for (int i=0; i<consultationArrayList.size(); i++) {
                            rowData[0] = consultationArrayList.get(i).doctor.getName();
                            rowData[1] = consultationArrayList.get(i).doctor.getSurname();
                            rowData[2] = String.valueOf(consultationArrayList.get(i).doctor.getDateOfBirth());
                            rowData[3] = String.valueOf(consultationArrayList.get(i).doctor.getMobileNumber());
                            rowData[4] = String.valueOf(consultationArrayList.get(i).doctor.getLicenceNumber());
                            rowData[5] = consultationArrayList.get(i).doctor.getSpecialisation();
                            rowData[6] = String.valueOf(consultationArrayList.get(i).patient.getPatientId());
                            rowData[7] = consultationArrayList.get(i).patient.getName();
                            rowData[8] = consultationArrayList.get(i).patient.getSurname();
                            rowData[9] = String.valueOf(consultationArrayList.get(i).patient.getDateOfBirth());
                            rowData[10] = String.valueOf(consultationArrayList.get(i).patient.getMobileNumber());
                            rowData[11] = consultationArrayList.get(i).getDate();
                            rowData[12] = consultationArrayList.get(i).getTime();
                            rowData[13] = consultationArrayList.get(i).getCost();
                            String notes = consultationArrayList.get(i).getNotes();
                            byte[] decryptNotes = Base64.getDecoder().decode(notes.getBytes());  //decrypt patient notes
                            rowData[14] = new String(decryptNotes);
                            model.addRow(rowData);
                        }
                    }
                }
            });
        }
        return jPanel;
    }
    private void invalidDate() {       //date validating option pane
        JOptionPane.showMessageDialog(this, "Enter a valid Date -> [dd/MM/yyyy]", "Invalid Date!", INFORMATION_MESSAGE);
    }

    @Override
    public String toString() {
        return "Consultation{" +
                "doctor=" + doctor +
                ", patient=" + patient +
                ", date=" + date +
                ", time=" + time +
                ", cost=" + cost +
                ", notes='" + notes + '\'' +
                '}';
    }
    //setters & getters
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getCost() {
        return cost;
    }
    public void setCost(String cost) {
        this.cost = cost;
    }
    public String getNotes() {
        return notes;
    }
    public void setNotes(String notes) {
        this.notes = notes;
    }
}
