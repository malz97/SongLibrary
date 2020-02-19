package songlib.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import songlib.app.Song;

public class SongLibUtil {
	
	public static ArrayList<Song> getSongsFromFile(String file) {
		
		ArrayList<Song> songlist = new ArrayList<Song>();
		BufferedReader br = null;
		String line = "";
		String cvsSplit = ",";
		
		try {
			
			br = new BufferedReader(new FileReader(file));
			line = br.readLine();
			while(line != null) {
				String[] temp = line.split(cvsSplit);
				Song song = new Song(temp[0], temp[1], "", 0);
				//Checks if an album was input
				if(!temp[2].equals("")) {
					song.setAlbum(temp[2]);
				}
				//Checks if a year was input
				if(!temp[3].equals("")) {
					Integer year = Integer.parseInt(temp[3]);
					song.setYear(year);
				}
				//Adds song to songlist arrayList
				songlist.add(song);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return songlist;
	}
}
