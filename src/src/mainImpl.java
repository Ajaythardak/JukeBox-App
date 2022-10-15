import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.sql.*;
import java.util.Scanner;

public class mainImpl
{

    public static void main(String[] args) throws SQLException, ClassNotFoundException, UnsupportedAudioFileException, LineUnavailableException, IOException

    {

        user userObj = new user();
        Songs songsObj = new Songs();
        Podcasts podCastObj = new Podcasts();
        Playlist playlistObj = new Playlist();
        songsPlaylist songsPlObj = new songsPlaylist();
        podcastPlaylist podCastPlObj = new podcastPlaylist();
        search searchObj=new search();
        Scanner scn = new Scanner(System.in);

        System.out.println("-------------------------- Welcome to JUKEBOX Application ----------------------------------");
        System.out.println();
        boolean ans1 = true;
        while (ans1)
        {
            System.out.println("Please Enter Your Choice number :- ");
            System.out.println("1 - 'To Listen'");
            System.out.println("2 - 'To Create your own Playlist'");
            System.out.println("3 - 'To view your already created Playlist'");
            System.out.println("4 - 'To search and listen to song/podcast'");
            System.out.println("5 - 'To close Application'");
            int choice = scn.nextInt();
            if (choice == 1)
            {
                System.out.println("Want to listen song or podcast?");
                System.out.println("1 - 'for Songs'");
                System.out.println("2 - 'for Podcasts'");
                int listen = scn.nextInt();

                if (listen == 1)
                {
                    songsObj.displaySongs();
                    System.out.println("Enter song Id of the song you want to listen.");
                    int songChoice = scn.nextInt();
                    String songPath = songsObj.getSongPath(songChoice);
                    //System.out.println(songPath);
                    try
                    {
                        Play audioPlayer = new Play(songPath);
                    }
                    catch (Exception ex)
                    {
                        System.out.println("Error with playing sound.");
                        ex.printStackTrace();
                    }
                }
                else if (listen == 2)
                {
                    podCastObj.displayPodcasts();
                    System.out.println("Enter Id of the Podcast u want to listen.");
                    int podcastChoice = scn.nextInt();
                    String pdPath = podCastObj.getPodcastPath(podcastChoice);
                    Play audioPlayer = new Play(pdPath);
                }
                else
                {
                    System.out.println("please enter a valid choice.");
                }

            }

            else if (choice == 2)
            {
                userObj.createAccount();
                System.out.println("want to create your playlist?");
                String plinput = scn.next();
                System.out.println("Enter your user id");
                int userID = scn.nextInt();

                if (plinput.equalsIgnoreCase("yes")) {
                    int playlistID = playlistObj.createPlaylist(userID);
                    System.out.println("Created playlists :-");
                    playlistObj.displayPlaylist(userID);


                } else {
                    System.out.println("these are your already created playlists.");
                    playlistObj.displayPlaylist(userID);
                }

                System.out.println("Enter Playlist Number of list you want to view.");
                int playlistNo = scn.nextInt();
                songsPlaylist.displaysongsPlaylist(playlistNo);
                podcastPlaylist.displayPodcastPlaylist(playlistNo);


            }
            else if (choice == 3)
            {

                Class.forName("com.mysql.cj.jdbc.Driver");
                String url = "jdbc:mysql://localhost:3306/jukeboxProject";
                Connection con = DriverManager.getConnection(url, "root", "root@123");
                Statement stmt2 = con.createStatement();
                System.out.println("Please enter your credentials to login into Application. ");
                System.out.println("Enter email Address");
                String email = scn.next();
                System.out.println("Enter your password");
                String password = scn.next();
                Class.forName("com.mysql.cj.jdbc.Driver");
                String query1 = "select password from user where email= '" + email + "' ;";
                ResultSet rs1 = stmt2.executeQuery(query1);
                String pswrd = null;

                while (rs1.next())
                {
                    pswrd = rs1.getString(1);
                }

                if (password.equalsIgnoreCase(pswrd))
                {
                    System.out.println("Welcome to your Account.");
                    String query3 = "select userID from user where email= '" + email + "' ;";
                    ResultSet rs3 = stmt2.executeQuery(query3);
                    int uId = 0;

                    while (rs3.next())
                    {
                        uId = rs3.getInt(1);
                    }

                    System.out.println("your user ID is :- " + uId);
                    System.out.println("-----------------------------------------------------------------------");

                    playlistObj.displayPlaylist(uId);
                    String songPath = songsObj.getSongPath(uId);
                    System.out.println("Enter Playlist Number of list you want to view/listen.");
                    int playlistNo = scn.nextInt();

                    songsPlaylist.displaysongsPlaylist(playlistNo);
                    podcastPlaylist.displayPodcastPlaylist(playlistNo);
                    System.out.println("do you want to add items in this playlist?");
                    String ans = scn.next();

                    if (ans.equalsIgnoreCase("yes"))
                    {
                        System.out.println("1 for songs");
                        System.out.println("2 for podcasts");
                        int choice2 = scn.nextInt();

                        if (choice2 == 1)
                        {
                            songsObj.displaySongs();
                            System.out.println("Enter song ID of song you want to add to " + playlistNo + " playlist.");
                            int songId = scn.nextInt();
                            songsPlObj.addSongstoPlaylist(playlistNo, songId);
                            songsPlObj.displaysongsPlaylist(playlistNo);
                            System.out.println("want to listen from this playlist?");
                            String ans2=scn.next();
                            if(ans2.equalsIgnoreCase("yes"))
                            {
                                System.out.println("1. to listen from songs plalist");
                                System.out.println("2. to listen from podcast playlist. ");
                                int choiceOfPl=scn.nextInt();
                                if(choiceOfPl==1)
                                {
                                    songsPlObj.playSong();
                                }
                                else if(choiceOfPl==2)
                                {
                                    podCastPlObj.playpodcast();
                                }
                            }



                        }
                        else if (choice2 == 2)
                        {
                            podCastObj.displayPodcasts();
                            System.out.println("Enter podcast ID of podcast you want to add to " + playlistNo + " playlist.");
                            int podCastId = scn.nextInt();
                            podCastPlObj.addPodcastToPlaylist(playlistNo, podCastId);
                        }
                        else
                        {
                            System.out.println("Enter valid choice.");
                        }
                    }

                    else
                    {

                        songsPlObj.displaysongsPlaylist(playlistNo);
                        podcastPlaylist.displayPodcastPlaylist(playlistNo);
                        System.out.println("want to listen from this playlist?");
                        String ans2=scn.next();
                        if(ans2.equalsIgnoreCase("yes"))
                        {
                            System.out.println("1. to listen from songs plalist");
                            System.out.println("2. to listen from podcast playlist. ");
                            int choiceOfPl=scn.nextInt();
                            if(choiceOfPl==1)
                            {
                                songsPlObj.playSong();
                            }
                            else if(choiceOfPl==2)
                            {
                                podCastPlObj.playpodcast();
                            }

                        }
                        else
                        {
                            System.out.println("Okay");
                        }
                    }
                }

                else
                {
                System.out.println("invalid credentials, try again later.");
                }

                System.out.println("---------------------------------------------------------------------------------");

            }

            else if(choice == 4)
            {

                System.out.println("what do you want to search?");
                System.out.println("1 - song");
                System.out.println("2 - podcast");
                int search=scn.nextInt();

                if(search==1)
                {
                    searchObj.searchSongAndPlay();
                }
                else if(search ==2)
                {
                    searchObj.searchPodcastAndPlay();
                }
                else
                {
                    System.out.println("invalid choice");
                }
            }

            else if (choice == 5)
            {
                ans1 = false;
                System.out.println(" Thanks for using our application.");
                break;
            }
            else
            {
                System.out.println("Enter valid choice.");
            }
        }
    }
}