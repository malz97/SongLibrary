package songlib.utility;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import javafx.collections.ObservableList;
import songlib.app.Song;

public class SongUtility {
	
	public static final String FILE_PATH = "songs.csv";
	
	public static ArrayList<Song> loadToList(String file) throws IOException {
		
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
		} catch(FileNotFoundException e) {
			PrintWriter csvWrite = new PrintWriter(FILE_PATH);
			csvWrite.flush();
			csvWrite.close();
			return list;
		}
		return list;
	}
	
	public static void save(ObservableList<Song> obsList) throws FileNotFoundException {
		PrintWriter writer = new PrintWriter(FILE_PATH);
		for (Song s : obsList) {
			StringBuilder sb = new StringBuilder();
			sb.append(s.getName());
			sb.append(",");
			sb.append(s.getArtist());
			sb.append(",");
			if(!s.getAlbum().isEmpty()) {
				sb.append(s.getAlbum());
				sb.append(",");
			}
			if(s.getYear() > 0) {
				sb.append(String.valueOf(s.getYear()));
				sb.append(",");
			}
			sb.append('\n');
			System.out.println(sb.toString());
			writer.write(sb.toString());
		}
		writer.flush();
		writer.close();
	}
}
