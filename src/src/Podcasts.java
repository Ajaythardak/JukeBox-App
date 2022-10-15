import java.sql.*;

public class Podcasts
{
    public void displayPodcasts()
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            Statement stmt = con.createStatement();
            String query = "select * from podcasts;";
            ResultSet rs = stmt.executeQuery(query);

            System.out.format("%5s %15s %20s %20s %20s %15s\n", "Podcast ID","Podcast Name","Duartion","Language","Episode ID","Artist ID");
            //System.out.println("'Song ID''Song Name'   'Duartion' 'Language' 'Artist ID' 'Album ID' 'Genre ID'");
            System.out.println("----------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {

                System.out.format("%2s %20s %20s %20s %20s %15s\n",rs.getInt(1),rs.getString(2),rs.getTime(3),rs.getString(4),rs.getInt(5),rs.getInt(6));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getPodcastPath(int pdChoice) throws ClassNotFoundException, SQLException
    {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukeboxProject";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        Statement stmt = con.createStatement();
        String query = "select filepath from podcasts where podcastID = " + pdChoice + ";";
        ResultSet rs = stmt.executeQuery(query);
        String podcastPath = null;
        while (rs.next()) {
            podcastPath = rs.getString(1);
        }
        return podcastPath;
    }


}
