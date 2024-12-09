package mk.ukim.finki.wp.lab.model;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class Song {
    private static Long idCounter = 0L;
    private Long id;
    public String trackId;
    public String title;
    public String genre;
    public int releaseYear;
    private List<Artist> performers;

    private Album album;

    public Song(String title, String trackId, String genre, int releaseYear, Long idAlbum) {
        this.title = title;
        this.trackId = trackId;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.id = idAlbum;
        this.performers = performers != null ? new ArrayList<>(performers) : new ArrayList<>();
    }


    Song() {
        this.performers = new ArrayList<>();
    }

    public Song(String trackId, String title, String genre, int releaseYear, List<Artist> performers) {
        this.id = generateUniqId();
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.performers = performers;
    }

    public void setAlbum(Album album) {
        if (this.album != null) {
            return;
        }
        this.album = album;
    }

    public Song(String trackId, String title, String genre, int releaseYear) {
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    private static synchronized Long generateUniqId() {
        return ++idCounter;
    }

    public void setArtist(Artist artist) {
        if (this.performers == null || !(this.performers instanceof ArrayList)) {
            this.performers = new ArrayList<>(this.performers);
        }
        this.performers.add(artist);
    }
}
