package songlib.app;

public class Song {

    private String name, artist, album;
    private int year;

    public Song (String name, String artist, String album, int year) {
        this.name = name;
        this.artist = artist;
        this.album = album;
        this.year = year;
    }

    public Song (String name, String artist, String album) {
        this(name, artist, album, 0);
    }

    public Song (String name, String artist, int year) {
        this(name, artist, "", year);
    }

    public Song (String name, String artist) {
        this(name, artist, "", 0);
    }

    public String getName() {
        return name;
    }

    public String getArtist() {
        return artist;
    }

    public String getAlbum() {
        return album;
    }

    public int getYear() {
        return year;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o){
            return true;
        }

        if(o == null || !(o instanceof Song)){
            return false;
        }

        Song song = (Song) o;
        return name.equalsIgnoreCase(song.name) && artist.equalsIgnoreCase(song.artist);
    }

    public String toString() {
        return name + " by " + artist;
    }
}
