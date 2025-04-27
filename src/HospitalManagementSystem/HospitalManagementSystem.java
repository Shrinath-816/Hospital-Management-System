package HospitalManagementSystem;

import java.sql.*;
import java.util.Scanner;

public class HospitalManagementSystem {

    private static final String url = "jdbc:mysql://localhost:3306/name_of_your_database";
    private static final String username = "your_username";
    private static final String password = "your-mysql-password";

    public static void main(String[] args){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");

        }catch(ClassNotFoundException e){
            e.printStackTrace();;
        }
        Scanner scanner = new Scanner(System.in);
        try{
            Connection connection = DriverManager.getConnection(url,username,password);
            Patient patient = new Patient(connection ,scanner);
            Doctors doctor = new Doctors(connection);
            while(true){
                System.out.println("HOSPITAL  MANAGEMENT  SYSTEM");
                System.out.println("1. Add Patients");
                System.out.println("2. View Patients");
                System.out.println("3. View Doctors");
                System.out.println("4. Book Appointment");
                System.out.println("5. Exit");
                System.out.println("Enter your choice");
                int choice = scanner.nextInt();

                switch (choice){
                    case 1:
                        patient.addPatient() ;
                        break;
                    case 2:
                        patient.viewPatient() ;
                        break;
                    case 3:
                        doctor.viewDoctors() ;
                        break;
                    case 4:
                        bookAppointment(patient,doctor,connection,scanner);
                        break;
                    case 5:
                        System.out.println("Thank you ");
                        return;
                    default:
                        System.out.println("Enter valid choice !!!!");
                        break;
                }

            }
        }catch(SQLException e){
            e.printStackTrace();
        }

    }
    public static void bookAppointment(Patient patient ,Doctors doctors ,Connection connection ,Scanner scanner ){
        System.out.println("Enter Patients ID : ");
        int patientId = scanner.nextInt();
        System.out.println("Enter Doctors ID : ");
        int doctorId = scanner.nextInt();
        System.out.println("Enter appointment date (YYYY-MM-DD) : ");
        String appointmentDate = scanner.next();
        if(patient.getPatientById(patientId) && doctors.getDoctorById(doctorId)){
            if(checkDoctorAvailibility(doctorId ,appointmentDate ,connection )){
                String appointmentQuery = "insert into appointments(patient_id,doctor_id,appointment_date) values(?,?,?);";
                try{
                    PreparedStatement preparedStatement = connection.prepareStatement(appointmentQuery);
                    preparedStatement.setInt(1,patientId);
                    preparedStatement.setInt(2,doctorId );
                    preparedStatement.setString(3,appointmentDate );
                    int rowsAffeted = preparedStatement.executeUpdate() ;
                    if(rowsAffeted >0){
                        System.out.println("Appointment booked");
                    }else{
                        System.out.println("Failed to book appointment");
                    }
                }catch(SQLException e){
                    e.printStackTrace();
                }
            }else{
                System.out.println("Doctor is not available on this date");
            }
        }else{
            System.out.println("Either Docctor or Patient doesn't exist");
        }
    }

    public static boolean checkDoctorAvailibility(int doctorId,String appointmentdate,Connection connection ){
        String query = "select count(*) from appointments where doctor_id = ? and appointment_date = ?";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query ) ;
            preparedStatement.setInt(1,doctorId);
            preparedStatement.setString(2,appointmentdate);
            ResultSet resultSet = preparedStatement.executeQuery() ;
            if(resultSet.next() ){
                int count = resultSet.getInt(1) ;
                if(count == 0){
                    return true;
                }else{
                    return false;
                }
            }

        }catch(SQLException e){
            e.printStackTrace() ;
        }
        return false;
    }


}
