package HospitalManagementSystem;

import com.mysql.cj.jdbc.exceptions.ConnectionFeatureNotAvailableException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Patient {

    private Connection connection;
    private Scanner scanner;

    public Patient(Connection connection,Scanner scanner){
       this.connection = connection;
        this.scanner = scanner;
    }

    public void addPatient(){
        System.out.println("Enter Patients Name :");
        String name = scanner.next();
        System.out.println("Enter Patients Age :");
        int age = scanner.nextInt();
        System.out.println("Enter Patients gender :");
        String gender = scanner.next();
        try{
            String query = "insert into patients(name,age,gender) values(?,?,?);";
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;
            preparedStatement.setString(1,name);
            preparedStatement.setInt(2,age);
            preparedStatement.setString(3,gender);
            int affectedrows = preparedStatement.executeUpdate() ;
            if(affectedrows > 0){
                System.out.println("Patient added successfully");
            }else{
                System.out.println("error occured while Patient getting added");
            }


        }catch(SQLException e){
            e.printStackTrace() ;
        }
    }

    public void viewPatient(){
        String query = "select * from patients;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;
            ResultSet resultSet = preparedStatement.executeQuery() ;
            System.out.println("List of all patients");
            System.out.println("+-------------+-------------------------+-------------+-------------+");
            System.out.println("| Patient Id  | Name                    | Age         | Gender       |");
            System.out.println("+-------------+-------------------------+-------------+-------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name");
                int age = resultSet.getInt("age") ;
                String gender = resultSet.getString("gender");
                System.out.printf("| %-11s | %-23s | %-11s | %-13s|\n",id,name,age,gender);
                System.out.println("+-------------+-------------------------+-------------+--------------+");
            }


        }catch(SQLException e){
            e.printStackTrace() ;
        }

    }

    public boolean getPatientById(int id){
        String query = "select * from patients where id = ?;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;
            preparedStatement.setInt(1,id );
            ResultSet resultSet = preparedStatement.executeQuery() ;
            if(resultSet.next()){
                return true;
            }else{
                return false;
                }
        }catch(Exception e){
             e.printStackTrace();
        }
        return false;
    }

}
