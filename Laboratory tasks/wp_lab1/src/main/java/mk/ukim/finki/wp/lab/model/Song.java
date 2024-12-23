package mk.ukim.finki.wp.lab.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Song {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String trackId;
    private String title;
    private String genre;
    private int releaseYear;

    @ManyToMany
    private List<Artist> performers;

    @ManyToOne
    private Album album;

    public Song(String trackId, String title, String genre, int releaseYear, List<Artist> performers) {
        this.trackId = trackId;
        this.title = title;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.performers = performers != null ? performers : new ArrayList<>();
    }


    public Song(String title, String trackId, String genre, int releaseYear, Long albumId) {
        this.title = title;
        this.trackId = trackId;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }

    public void setAlbum(Album album) {
        if (this.album != null) {
            return;
        }
        this.album = album;
    }

    public void setArtist(Artist artist) {
        if (this.performers == null || !(this.performers instanceof ArrayList)) {
            this.performers = new ArrayList<>(this.performers);
        }
        this.performers.add(artist);
    }

    @Override
    public String toString() {
        return id + " " + trackId + " " + title + " " + genre + " " + releaseYear;
    }
}
