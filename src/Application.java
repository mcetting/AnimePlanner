import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class Application{
	
	// loads a file and returns a list of strings to the main method
	private static List<String> LoadFile() throws IOException {
		String filename = "anime.txt";
		List<String> series = new ArrayList();
		try {
			// the file as found
			BufferedReader reader = new BufferedReader(new FileReader(filename));
			
			// read the list
			String content = "";
			while((content = reader.readLine()) != null) {
				series.add(content);
			}
		} catch (FileNotFoundException e) {
			// the file wasnt found
			System.out.println("The file wasnt found");
			// create a new file
			File f = new File("anime.txt");
			if(f.createNewFile()) {
				System.out.println("New file created.");
				LoadFile();
			}
		}
		return series;
	}
	
	// simply prints the list in the console
	private static void PrintList(List<String> list) {
		System.out.println("Printing!");
		for(int i = 0; i < list.size(); i ++) {
			System.out.println(list.get(i));
		}
	}
	
	// music
	public static void PlaySong() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("song.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	        clip.loop(10);
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	
	// blip
	public static void PlayBlip() {
	    try {
	        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File("click.wav").getAbsoluteFile());
	        Clip clip = AudioSystem.getClip();
	        clip.open(audioInputStream);
	        clip.start();
	    } catch(Exception ex) {
	        System.out.println("Error with playing sound.");
	        ex.printStackTrace();
	    }
	}
	public static void LoadWebsite(String url) throws IOException, URISyntaxException {
		Desktop d = Desktop.getDesktop();
		d.browse(new URI(url));
	}
	
	public static void main(String[] args) throws IOException {
		List<String> animeSeries = LoadFile();
		Program program = new Program(animeSeries);
		PlaySong();
	}

}

