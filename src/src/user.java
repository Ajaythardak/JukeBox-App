import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Random;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class user {
    public void createAccount() {
        Scanner scn = new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            System.out.println("*To create your own customised playlist, you need to have an Account in our app.*");
            System.out.println("So, Are you an existing user? ");
            String exists = scn.next();
            Statement stmt = con.createStatement();
//            Statement stmt = null;
            if (exists.equalsIgnoreCase("no")) {
                System.out.println("You have to signUp first.");
                System.out.println("Please enter your credentials for account creation:- ");
                System.out.println("Enter your Name");
                String name = scn.next();
                System.out.println("Enter your Phone Number");
                long phone = scn.nextLong();
                String finalEmail=null;
                boolean b1;
                do {
                    System.out.println("Enter email Address");
                    String email = scn.next();

                    b1 = Pattern.matches("[a-z]+[0-9]+[@][a-z]+.[a-z]{3}", email);
                    if (b1 == false) {
                        System.out.println("Enter valid email address, Try again.");
                    }
                    else{
                        finalEmail=email;
                    }
                }
                while(b1!=true);

                String finalPswrd=null;
                boolean b2=false;
                while(b2!=true) {
                    System.out.println("Enter a suitable password");
                    String password = scn.next();

                    b2 = Pattern.matches("[a-z]+@[1-9]+", password);

                    if (b2 == false) {
                        System.out.println("invalid password, try again.");
                    }
                    else
                    {
                        finalPswrd=password;
                    }
                }

                    String query = "insert into user (Name, phone, email, password) values(?,?,?,?)";

                    PreparedStatement ps = con.prepareStatement(query);
                    ps.setString(1, name);
                    ps.setLong(2, phone);
                    ps.setString(3, finalEmail);
                    ps.setString(4, finalPswrd);
                    int check = ps.executeUpdate();

                    System.out.println("Account successfully created.");

                    String query2 = "select userID from user where email= '" + finalEmail + "' ;";
                    ResultSet rs2 = stmt.executeQuery(query2);
                    while (rs2.next()) {
                        System.out.println("Your user Id is :- " + rs2.getInt(1));
                    }

                } else if (exists.equalsIgnoreCase("yes")) {
                    System.out.println("Please enter your credentials to login into Application. ");
                    System.out.println("Enter email Address");
                    String email = scn.next();
                    System.out.println("Enter your password");
                    String password = scn.next();
                    Class.forName("com.mysql.cj.jdbc.Driver");
                    String query1 = "select password from user where email= '" + email + "' ;";
                    ResultSet rs1 = stmt.executeQuery(query1);
                    String pswrd = null;
                    while (rs1.next()) {
                        pswrd = rs1.getString(1);
                    }
                    if (password.equalsIgnoreCase(pswrd)) {
                        System.out.println("Welcome to your Account.");
                        String query3 = "select userID from user where email= '" + email + "' ;";
                        ResultSet rs3 = stmt.executeQuery(query3);
                        int uId = 0;
                        while (rs3.next()) {
                            uId = rs3.getInt(1);
                        }
                        System.out.println("your user ID is :- " + uId);
                        System.out.println("-----------------------------------------------------------------------");
//                    System.out.println("Now what do you want to do.?");
//                    System.out.println("1. 'to create new playlist' ");
//                    System.out.println("2. 'to see existing playlist' ");
                    }
                } else {
                    System.out.println("invalid credentials, try again later.");
                }

        }catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        }
    }




