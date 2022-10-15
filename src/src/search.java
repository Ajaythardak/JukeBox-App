import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class search
{
    public void searchSongAndPlay()
    {
        Scanner sc=new Scanner(System.in);
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");
            String fsongName=null;
            int a=1;
            do {
                System.out.println("Enter the song Name ");
                String songName = sc.nextLine();

                String q2 = "SELECT * FROM songs where sName= ? ;";
                PreparedStatement ps2 = con.prepareStatement(q2);
                ps2.setString(1, songName);
                ResultSet res = ps2.executeQuery();
                if (res.next()) {
//                    while (res.next()) {

                        System.out.println("Song's Id  :  " + res.getInt(1) + "     " + "Song Name  : " + res.getString(2) +
                                "     " + " Song  Duration  : " + res.getString(3));
                        fsongName=songName;
                        a=0;
                    //}
                } else {
                    System.out.println("there is no audio clip for your choice.");
                }
            }
            while(a!=0);


            System.out.println("want to play this song ");
            String ans=sc.next();
            if(ans.equalsIgnoreCase("yes")) {

                String q3 = "select filepath from songs where sName='" + fsongName + "';";
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery(q3);
                String filepath = null;
                while (rs1.next()) {
                    filepath = rs1.getString(1);
                }

                Play play = new Play(filepath);
            }


        } catch (NullPointerException ne)
        {
            ne.printStackTrace();
        }
        catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

    }

    public void searchPodcastAndPlay()
    {
        Scanner sc=new Scanner(System.in);
        try {

            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/jukeboxProject";
            Connection con = DriverManager.getConnection(url, "root", "root@123");

            String fpodcastName=null;
            int a=1;
            do {
            System.out.println("Enter the podcast Name ");
            String podcastName = sc.nextLine();


            String q2 = "SELECT * FROM podcasts where podcastName= ? ;";
            PreparedStatement ps2 = con.prepareStatement(q2);
            ps2.setString(1, podcastName);
            ResultSet res2 = ps2.executeQuery();
            if (res2.next())
            {
                System.out.println("Podcast Id  :  "+res2.getInt(1)+"     "+"Episode Name  : "+res2.getString(2)+ "     "+" Duration  : "+res2.getTime(3));
                fpodcastName=podcastName;
                a=0;
            }
            else
            {
                System.out.println("there is no podcast clip for your choice.");
            }
            }
            while(a!=0);


            System.out.println("want to play this podcast? ");
            String ans=sc.next();
            if(ans.equalsIgnoreCase("yes")) {
                String q3 = "select filepath from podcasts where podcastName='" + fpodcastName + "';";
                Statement stmt = con.createStatement();
                ResultSet rs1 = stmt.executeQuery(q3);
                String filepath = null;
                while (rs1.next()) {
                    filepath = rs1.getString(1);
                }

                Play play = new Play(filepath);
            }


        } catch (SQLException e) {
            e.printStackTrace();
        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        catch(NullPointerException e)
        {
            e.printStackTrace();
        }

    }
}
