import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class podcastPlaylist
{
    static Scanner scn=new Scanner(System.in);
    public static void addPodcastToPlaylist(int playlistNo, int podCastId) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukeboxProject";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        Statement stmt = con.createStatement();
        String query = "insert into podcastPlaylist ( podcastID, playlistNo) values(?,?);";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, podCastId);
        ps.setInt(2, playlistNo);
        int check = ps.executeUpdate();
        System.out.println("Podcast added successfully to your playlist.");
    }
    public static void displayPodcastPlaylist(int playlistNo)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            Statement stmt = con.createStatement();
            String query = "select a.podcastPlID, a.podcastID, a.playlistNo, b.podcastName, b.pduration, c.episodeName, c.onAirDate, d.pArtistName from podcastPlaylist a join podcasts b on a.podcastID=b.podcastID join podcastEpisode c on b.episodeID=c.episodeID join podcastArtist d on b.pArtistID=d.pArtistID where playlistNo="+playlistNo+";";
            ResultSet rs = stmt.executeQuery(query);

            System.out.format("%5s %15s %20s %20s %20s %15s %15s %15s\n", "PodcastPl ID","Podcast ID","Playlist No.","Podcast Name","Duration","Episode","Release Date","Artist Name");
            //System.out.println("'Song ID' 'Song Name'  'Duration' 'Language' 'Artist ID' 'Album ID' 'Genre ID'");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {

                System.out.format("%2s %20s %20s %20s %20s %15s %15s %15s\n",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getTime(5),rs.getString(6),rs.getDate(7),rs.getString(8));
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void playpodcast() throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukeboxProject";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        Statement stmt = con.createStatement();
        System.out.println("Enter podcast ID of the podcast you want to play.");

        int podcastId=scn.nextInt();

        String query = "select filepath from podcasts where podcastID="+podcastId+";";
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next())
        {
            String path=rs.getString(1);
            Play audioPlayer = new Play(path);
        }
    }
}

