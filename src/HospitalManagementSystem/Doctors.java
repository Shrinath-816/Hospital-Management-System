package HospitalManagementSystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Doctors {

    private Connection connection;



    public Doctors (Connection connection){
        this.connection = connection;

    }



    public void viewDoctors(){
        String query = "select * from DOCTORS;";
        try{
            PreparedStatement preparedStatement = connection.prepareStatement(query) ;
            ResultSet resultSet = preparedStatement.executeQuery() ;
            System.out.println("List of all Doctors");
            System.out.println("+-------------+-------------------------+-------------------------+");
            System.out.println("| Doctor Id   | Name                    |  Specialization         |");
            System.out.println("+-------------+-------------------------+-------------------------+");
            while(resultSet.next()){
                int id = resultSet.getInt("id") ;
                String name = resultSet.getString("name");
                String specialization = resultSet.getString("specialization");
                System.out.printf("| %-11s | %-23s | %-24s|\n",id,name,specialization);
                System.out.println("+------------+------------------------+--------------------------+");
            }


        }catch(SQLException e){
            e.printStackTrace() ;
        }

    }

    public boolean getDoctorById(int id){
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
