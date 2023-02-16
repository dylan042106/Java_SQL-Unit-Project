package org.JavaSQLUnitProject;
import java.sql.*;
import java.util.*;

public class Main {
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static void main(String[] args) throws SQLException {
        Connection conn = ConnectionToDB();
        if (conn != null) {
            LoginAndRegistration(conn);
        } else {
            System.out.println("Error with connection. Ending Program");
        }
    }


    public static void LoginAndRegistration(Connection conn) throws SQLException {
        typewrite(ANSI_CYAN + "Welcome to St. Bonaventure Hospital" + ANSI_RESET);
        typewrite(ANSI_CYAN + "Patient or Nurse?");
        System.out.print("> ");
        Scanner loginInput = new Scanner(System.in);
        String answerFromLoginInput = loginInput.nextLine();
        if (Objects.equals(answerFromLoginInput, "Nurse")) {
            typewrite("Login or Register?");
            System.out.print("> ");
            Scanner loginRegisterNurseInput = new Scanner(System.in);
            String answerFromLoginRegisterNurseInput = loginRegisterNurseInput.nextLine();
            if (Objects.equals(answerFromLoginRegisterNurseInput, "Login")) {
                loginAsNurse(conn);
            } else if (Objects.equals(answerFromLoginRegisterNurseInput, "Register")) {
                registerNewNurse(conn);
            }
        } else if (Objects.equals(answerFromLoginInput, "Patient")) {
            System.out.println("Not yet available");
        }

    }

    public static void registerNewNurse(Connection conn) throws SQLException {
        typewrite(ANSI_CYAN + "Please input Nurse Registration Number to continue with registration");
        System.out.print("> ");
        Scanner inputCode = new Scanner(System.in);
        String userCodeInput = inputCode.nextLine();
        String getNurseCode = "SELECT * FROM nurse_registration_code";
        Statement stmt = conn.createStatement();
        ResultSet rs = stmt.executeQuery(getNurseCode);
        while (rs.next()){
            String nurseRegistrationCode = rs.getString("code");
            if (userCodeInput.equals(nurseRegistrationCode)){
                typewrite("Code Accepted");
                System.out.println("Redirecting");
                typewrite(". . . . . . . .");
                System.out.println();
                typewriteOther("First Name: ");
                Scanner firstNameInput = new Scanner(System.in);
                String UserFirstNameInput = firstNameInput.nextLine();
                System.out.println(UserFirstNameInput);
                System.out.println();
                typewriteOther("Last Name: ");
                Scanner lastNameInput = new Scanner(System.in);
                String UserLastNameInput = lastNameInput.nextLine();
                System.out.println();
                typewriteOther("Email: ");
                Scanner emailInput = new Scanner(System.in);
                String UserEmailInput = emailInput.nextLine();
                System.out.println();
                typewriteOther("Password: ");
                Scanner passInput = new Scanner(System.in);
                String UserPassInput = passInput.nextLine();
                insertNurse(UserFirstNameInput, UserLastNameInput, UserEmailInput, UserPassInput, conn);
            } else {
                System.out.println("Incorrect Code");
            }
        }

    }

    public static void loginAsNurse(Connection conn) throws SQLException {
        typewrite("Please input your email associated with your account");
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
                    typewrite("Please input password associated with account");
                    System.out.print("> ");
                    Scanner passInput = new Scanner(System.in);
                    String userPassInput = passInput.nextLine();
                    for (String pass : nursePass) {
                        if (userPassInput.equals(pass)) {
                            typewrite("Login Successful!");
                        } else{
                            typewrite("Password Incorrect");
                        }
                    }
                } else {
                    typewrite("Could not find email. Please check typing or register new account");
                }
            }
        }
    }

    public static void insertNurse(String first, String last, String email, String pass, Connection conn) throws SQLException {
        String insertSql = "INSERT INTO nurse_info(nurse_first_name, nurse_last_name, nurse_email, nurse_pass)" +
                "VALUES ('" + first + "', '" + last + "', '" + email + "', '" + pass + "');";
        Statement stmt = conn.createStatement();
        PreparedStatement preStmt = conn.prepareStatement(insertSql);
        preStmt.execute();
        System.out.println("Successfully added nurse");
    }
    
    public static void typewrite(String input) {
        for (int i = 0; i < input.length(); i++) {
            System.out.print(input.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        System.out.println();
    }

    public static void typewriteOther(String input) {
        for (int i = 0; i < input.length(); i++) {
            System.out.print(input.charAt(i));
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
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
            System.out.println();
            System.out.println(ANSI_GREEN + "Connection Established");
            System.out.println();
            return conn;
        } catch (Exception e){
            System.out.println(e);
            return null;
        }
    }
}