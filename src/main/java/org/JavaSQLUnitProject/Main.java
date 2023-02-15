package org.JavaSQLUnitProject;
import java.sql.*;

public class Main {
    public static void main(String[] args) {
        ConnectionToDB();
        System.out.println("Welcome to St. Bonaventure Hospital");
        System.out.println("Would you like to login as a patient or nurse?");
    }

    public static void ConnectionToDB() {
        String myDriver = "org.postgresql.Driver";
        String dbURL = "jdbc:postgresql://localhost:5432/hospital_registration";
        String dbUser = "dylan_herring";
        String dbPass = "062104";

        try{
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            System.out.println("Connection Established");
        } catch (Exception e){
            System.out.println(e);
        }
    }
}