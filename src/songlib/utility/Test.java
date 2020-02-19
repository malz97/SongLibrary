package songlib.utility;

import java.util.ArrayList;

import songlib.app.Song;

public class Test {
	
	public static void main(String[] args) {
		String csv = "/home/malz97/eclipse-workspace/SongLibrary/src/songlib/utility/songs.csv";
		ArrayList<Song> list = new ArrayList<Song>();
		list = SongLibUtil.getSongsFromFile(csv);
		
		System.out.println(list.get(0));
	}
}
