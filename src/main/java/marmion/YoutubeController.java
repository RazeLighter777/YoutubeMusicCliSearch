package marmion;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;

public class YoutubeController {

    Runtime rt;

    public YoutubeController(Runtime rt) {
        this.rt = rt;
        //Check that the executable is present.
        try {
            assert(rt.exec("youtube-dl").exitValue() == 0);
        } catch (IOException e) {
            System.out.println("There was an IO Exception");
        }
    }

    public void removeNonMusic() {
        try {
            Process p =  rt.exec("rm -rf music/");
            Process q = rt.exec("mkdir music");
            BufferedReader in =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
            p.waitFor();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void playCachedSongs() {
        List<String> results = new ArrayList<String>();


        File[] files = new File("music/").listFiles();
        //If this pathname does not denote a directory, then listFiles() returns null. 

        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().endsWith(".mp3")) {

                    results.add(file.getName());
                }

            }
        }
        for (String s : results) {
            try {
                Process p = rt.exec("mpv " + "music/" + s);
                BufferedReader in =
                new BufferedReader(new InputStreamReader(p.getInputStream()));
                String inputLine;
                while ((inputLine = in.readLine()) != null) {
                    System.out.println(inputLine);
                }
                in.close();
                p.waitFor();
            } catch (IOException | InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    public void getSearchResult(String search) {
        removeNonMusic();
        try {
            //String query = "./youtube-dl -k --audio-format mp3 --max-downloads 1 --rm-cache-dir --extract-audio --default-search ytsearch1 \""+ search + "\" -o music/thing.mp3";
            String query = "./youtube-dl --default-search=ytsearch \'" + search.replace(" ", "+")+ "\' -k --audio-format mp3 --max-downloads 1 --rm-cache-dir --extract-audio -o music/thing.mp3";

            System.out.println(query);
            Process p = rt.exec(query);
            
            BufferedReader in =
            new BufferedReader(new InputStreamReader(p.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                System.out.println(inputLine);
            }
            in.close();
            p.waitFor();
        } catch (IOException e) {
            System.out.println("There was an IO Exception");
        }
        catch (InterruptedException e) {
            System.out.println(e);
        }
        playCachedSongs();
    }
}