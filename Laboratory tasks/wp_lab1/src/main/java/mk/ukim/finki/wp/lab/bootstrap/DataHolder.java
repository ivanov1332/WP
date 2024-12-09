package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static final List<Artist> artistList = new ArrayList<>();
    public static final List<Song> songList = new ArrayList<>();
    public static final List<Album> albums = new ArrayList<>();

    @PostConstruct
    public void init() {
        for (int i = 0; i < 5; i++) {
            artistList.add(new Artist(i, "FirstName" + i, "LastName" + i, "Bio" + i));
            songList.add(new Song("track" + i, "title" + i, "genre" + i, 2000 + i, List.of(artistList.get(i))));
            albums.add(new Album("name" + i, (long) i, "genre" + i, "201" + i));

            songList.get(i).setAlbum(albums.get(i));
        }
    }
}
