package org.JavaSQLUnitProject;
import java.sql.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws SQLException {
        Connection conn = ConnectionToDB();
        if (conn != null) {
            LoginAndRegistration(conn);
        } else {
            System.out.println("Error with connection. Ending Program");
        }
    }


    public static void LoginAndRegistration(Connection conn) throws SQLException {
        System.out.println("Welcome to St. Bonaventure Hospital");
        System.out.println("Patient or Nurse?");
        System.out.print("> ");
        Scanner loginInput = new Scanner(System.in);
        String answerFromLoginInput = loginInput.nextLine();
        if (Objects.equals(answerFromLoginInput, "Nurse")) {
            System.out.println("Login or Register?");
            System.out.print("> ");
            Scanner loginRegisterNurseInput = new Scanner(System.in);
            String answerFromLoginRegisterNurseInput = loginRegisterNurseInput.nextLine();
            if (Objects.equals(answerFromLoginRegisterNurseInput, "Login")) {
                loginAsNurse(conn);
            } else if (Objects.equals(answerFromLoginRegisterNurseInput, "Register")) {
                registerNewNurse();
            }
        } else if (Objects.equals(answerFromLoginInput, "Patient")) {
            System.out.println("Not yet available");
        }

    }

    public static void registerNewNurse(){
        System.out.println("Please input Nurse Registration Number to continue with registration");
    }

    public static void loginAsNurse(Connection conn) throws SQLException {
        System.out.println("Please input your email associated with your account");
        System.out.print("> ");
        Scanner emailInput = new Scanner(System.in);
        String userInputEmail = emailInput.nextLine();
        String getNurseInfo = "SELECT * FROM nurse_info";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(getNurseInfo);
        while (rs.next()){
            String[] nurseEmails = new String[]{rs.getString("nurse_email")};
            String[] nursePass = new String[]{rs.getString("nurse_pass")};
            for (String nurseEmail : nurseEmails) {
                if (userInputEmail.equals(nurseEmail)) {
                    System.out.println("Please input password associated with account");
                    System.out.print("> ");
                    Scanner passInput = new Scanner(System.in);
                    String userPassInput = passInput.nextLine();
                    for (String pass : nursePass) {
                        if (userPassInput.equals(pass)) {
                            System.out.println("Login Successful!");
                        } else{
                            System.out.println("Password Incorrect");
                        }
                    }
                } else {
                    System.out.println("Could not find email. Please check typing or register new account");
                }
            }
        }
    }
    public static Connection ConnectionToDB() {
        String myDriver = "org.postgresql.Driver";
        String dbURL = "jdbc:postgresql://localhost:5432/hospital_registration";
        String dbUser = "dylan_herring";
        String dbPass = "062104";

        try{
            Class.forName(myDriver);
            Connection conn = DriverManager.getConnection(dbURL, dbUser, dbPass);
            System.out.println("Connection Established");
            return conn;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}