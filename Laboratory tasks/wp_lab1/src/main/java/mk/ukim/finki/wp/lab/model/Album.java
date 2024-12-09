package mk.ukim.finki.wp.lab.model;

import lombok.Data;
import java.util.ArrayList;
import java.util.List;

@Data
public class Album {

    private Long id;
    private String name;
    private String genre;
    private String releaseYear;
    List<Song> songs = new ArrayList<>();

    public Album(String name, Long id, String genre, String releaseYear) {
        this.name = name;
        this.id = id;
        this.genre = genre;
        this.releaseYear = releaseYear;
    }
}
