import java.sql.*;
import java.util.Scanner;

public class Playlist {
    Scanner scn = new Scanner(System.in);

    public int createPlaylist(int userID) {
        int plId =0;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            //Statement stmt = null;
            System.out.println("Enter name for the Playlist ");
            String playlistName = scn.next();
            String query = "insert into Playlist (playlistName, userID) values(?,?);";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, playlistName);
            ps.setLong(2, userID);
            int check = ps.executeUpdate();
            System.out.println("Playlist created.");

            Statement stmt2 = con.createStatement();
            String query2 = "select playlistNo from Playlist;";
            ResultSet rs2 = stmt2.executeQuery(query2);
            while(rs2.next())
            {
                plId = rs2.getInt(1);
            }
            System.out.println(plId);
            return plId;
        } catch (SQLException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return plId;
    }

    public void displayPlaylist(int uId)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            Statement stmt = con.createStatement();
            String query = "select * from Playlist where userID="+uId+";";
            ResultSet rs = stmt.executeQuery(query);
            System.out.format("%5s %15s %20s\n", "Playlist No.","Playlist Name","user ID");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {

                System.out.format("%2s %20s %20s\n", rs.getInt(1),rs.getString(2),rs.getInt(3));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

