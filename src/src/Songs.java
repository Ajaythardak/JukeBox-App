import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;

public class Songs
{
    public void displaySongs()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            Statement stmt = con.createStatement();
            String query = "select * from songs order by sname;";
            ResultSet rs = stmt.executeQuery(query);

            System.out.format("%5s %15s %20s %20s %20s %15s %15s\n", "Song ID","Song Name","Duration","Language","Artist ID","Album ID","Genre ID");
            //System.out.println("'Song ID''Song Name'   'Duartion' 'Language' 'Artist ID' 'Album ID' 'Genre ID'");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {

                System.out.format("%2s %20s %20s %20s %20s %15s %15s\n",rs.getInt(1),rs.getString(2),rs.getTime(3),rs.getString(4),rs.getInt(5),rs.getInt(6),rs.getInt(7));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public String getSongPath(int songChoice) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukeboxProject";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        Statement stmt = con.createStatement();
        String query = "select filepath from songs where songID = " + songChoice + ";";
        ResultSet rs = stmt.executeQuery(query);
        String songPath = null;
        while (rs.next()) {
            songPath = rs.getString(1);
        }
        return songPath;
    }
}


















