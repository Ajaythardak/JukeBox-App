import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Play {

    // to store current position
    Long currentFrame;
    Clip clip;

    // current status of clip
    String status;

    AudioInputStream audioInputStream;
    static String filePath;

    public Play()
    {

    }

    public  Play(String filePath) throws UnsupportedAudioFileException, LineUnavailableException, IOException {

        this.filePath=filePath;
        // Play p=new Play();
        working();
    }

    public void working()
    {
        try {

            // create AudioInputStream object
            //System.out.println(filePath);
            if(!filePath.equals(null)) {
                audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
            }
            else {
                System.out.println("there is no audio clip for your choice.");
            }
            // create clip reference
            clip = AudioSystem.getClip();
            //System.out.println("clip is : " + clip);

            // open audioInputStream to the clip
            clip.open(audioInputStream);

            clip.loop(Clip.LOOP_CONTINUOUSLY);
            Scanner sc = new Scanner(System.in);
            System.out.println();
//            Play audioPlayer =
//                    new Play();
            play();
            //status="play";
            while (true)
            {
                System.out.println("1. pause");
                System.out.println("2. resume");
                System.out.println("3. restart");
                System.out.println("4. stop");
                int c = sc.nextInt();
                gotoChoice(c);
                if (c == 4)
                    break;
            }
            //sc.close();

        } catch (UnsupportedAudioFileException e) {
            e.printStackTrace();
        } catch (LineUnavailableException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        catch (Exception ex)
        {
            System.out.println("Error with playing sound.");
            ex.printStackTrace();

        }
    }

    void gotoChoice(int c) throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        switch (c)
        {
            case 1:
                pause();
                break;
            case 2:
                resumeAudio();
                break;
            case 3:
                restart();
                break;
            case 4:
                stop();
                break;
            default:
                System.out.println("enter a valid choice.");
                break;
        }
    }

    // Method to play the audio
    public void play()
    {
        //start the clip
        clip.start();
        status = "play";
    }

    // Method to pause the audio
    public void pause()
    {
        if (status.equals("paused"))
        {
            System.out.println("audio is already paused");
            return;
        }
        this.currentFrame = this.clip.getMicrosecondPosition();
        clip.stop();
        status = "paused";
    }

    // Method to resume the audio
    public void resumeAudio() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if (status.equals("play"))
        {
            System.out.println("Audio is already "+ "being played");
            return;
        }
        clip.close();
        resetAudioStream();
        clip.setMicrosecondPosition(currentFrame);
        this.play();
    }

    // Method to restart the audio
    public void restart() throws IOException, LineUnavailableException, UnsupportedAudioFileException
    {
        clip.stop();
        clip.close();
        resetAudioStream();
        currentFrame = 0L;
        clip.setMicrosecondPosition(0);
        this.play();
    }

    // Method to stop the audio
    public void stop() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        currentFrame = 0L;
        clip.stop();
        clip.close();
    }

    // Method to jump over a specific part
    public void jump(long c) throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        if (c > 0 && c < clip.getMicrosecondLength())
        {
            clip.stop();
            clip.close();
            resetAudioStream();
            currentFrame = c;
            clip.setMicrosecondPosition(c);
            this.play();
        }
    }

    // Method to reset audio stream
    public void resetAudioStream() throws UnsupportedAudioFileException, IOException, LineUnavailableException
    {
        audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
        clip.open(audioInputStream);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
}