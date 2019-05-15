package marmion;

/**
 * Hello world!
 *
 */
import java.util.Scanner;

public class App 
{
    static Runtime rt; 

    static YoutubeController ytCon;

    static void askForNextSong() {
        System.out.print("Enter the name of the song: ");
        Scanner sc = new Scanner(System.in);
        String query = sc.nextLine();
        ytCon.getSearchResult(query);
        sc.close();
    }
    public static void main( String[] args )
    {
        rt = Runtime.getRuntime();
        ytCon = new YoutubeController(rt);
        ytCon.removeNonMusic();
        System.out.println(System.getProperty("user.dir"));
        askForNextSong();
    }
}
