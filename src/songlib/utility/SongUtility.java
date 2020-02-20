package songlib.utility;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import songlib.app.Song;

public class SongUtility {
	
	public static ArrayList<Song> loadToList(String file) {
		
		ArrayList<Song> list = new ArrayList<Song>();
		String line = "";
		String splitTok = ",";
		
		try(BufferedReader br = new BufferedReader(new FileReader(file))) {
			while((line = br.readLine()) != null) {
				String[] tmp = line.split(splitTok);
				Song s = new Song(tmp[0], tmp[1]);
				if(tmp.length > 2) {
					//Check if album had been entered
					if(!tmp[2].equals("")) {
						s.setAlbum(tmp[2]);
					}
					//Check if year had been entered
					if(!tmp[3].equals("")) {
						int year = Integer.parseInt(tmp[3]);
						s.setYear(year);
					}
				}
				list.add(s);
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
		return list;
	}
}
