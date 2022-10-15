import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class songsPlaylist
{
    static Scanner scn=new Scanner(System.in);
    public static void displaysongsPlaylist(int playlistNo)
    {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            Statement stmt = con.createStatement();
            String query = "select a.songplID, a.songID, a.playlistNo, b.sName, b.sduration, c.aName, d.albumName, e.genreType from songsplaylist a join songs b on a.songID=b.songID join artist c on b.artistID=c.artistID join album d on b.albumID=d.albumID join genre e on b.genreID=e.genreID where playlistNo="+playlistNo+";";
            ResultSet rs = stmt.executeQuery(query);
            //PreparedStatement ps = con.prepareStatement(query);
            System.out.format("%5s %15s %20s %20s %20s %15s %15s %15s\n", "SongPl ID","Song ID","Playlist No.","Song Name","Duration","Artist","Album","Genre");
            //System.out.println("'Song ID''Song Name'   'Duartion' 'Language' 'Artist ID' 'Album ID' 'Genre ID'");
            System.out.println("--------------------------------------------------------------------------------------------------------------------------------");
            while (rs.next()) {

                System.out.format("%2s %20s %20s %20s %20s %15s %15s %15s\n",rs.getInt(1),rs.getInt(2),rs.getInt(3),rs.getString(4),rs.getTime(5),rs.getString(6),rs.getString(7),rs.getString(8));
            }
//            System.out.println(rs);
//            int check= ps.executeUpdate(query);
//            System.out.println(check);
//            System.out.println("do you want to listen from this playlist?");
//            String choice=scn.next();
//            if(choice.equalsIgnoreCase("yes")) {
//                String songPath = songsObj.getSongPath(uId);
//                Play audioPlayer = new Play(songPath);
  //          }

        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void addSongstoPlaylist(int playlistNo, int songID) throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukeboxProject";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        //Statement stmt = con.createStatement();
        String query = "insert into songsPlaylist ( songID, playlistNo) values(?,?);";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setInt(1, songID);
        ps.setInt(2, playlistNo);
        int check = ps.executeUpdate();
        System.out.println("Song added successfully to your playlist.");
    }

    public void playSong() throws ClassNotFoundException, SQLException, UnsupportedAudioFileException, LineUnavailableException, IOException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        String url = "jdbc:mysql://localhost:3306/jukeboxProject";
        Connection con = DriverManager.getConnection(url, "root", "root@123");
        Statement stmt = con.createStatement();
        System.out.println("Enter song ID of the song you want to play.");
        int songId=scn.nextInt();

        String query = "select filepath from songs where songID="+songId+";";
        ResultSet rs = stmt.executeQuery(query);

        while(rs.next())
        {
            String path=rs.getString(1);
            Play audioPlayer = new Play(path);
        }

    }
}
