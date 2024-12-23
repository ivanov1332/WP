package mk.ukim.finki.wp.lab.bootstrap;

import jakarta.annotation.PostConstruct;
import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.AlbumRepository;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataHolder {
    public static final List<Artist> artistList = new ArrayList<>();
    public static final List<Song> songList = new ArrayList<>();
    public static final List<Album> albums = new ArrayList<>();

    private final AlbumRepository albumRepository;
    private final SongRepository songRepository;
    private final ArtistRepository artistRepository;

    public DataHolder(AlbumRepository albumRepository, SongRepository songRepository, ArtistRepository artistRepository) {
        this.albumRepository = albumRepository;
        this.songRepository = songRepository;
        this.artistRepository = artistRepository;
    }

    @PostConstruct
    public void init() {

        for (int i = 0; i < 5; i++) {
            Artist artist = new Artist("FirstName" + (i + 1), "LastName" + (i + 1), "Bio" + (i + 1));
            artistList.add(artist);
        }
        artistRepository.saveAll(artistList);

        for (int i = 0; i < 5; i++) {
            Album album = new Album("name" + (i + 1), "genre" + (i + 1), "201" + (i + 1));
            albums.add(album);
        }
        albumRepository.saveAll(albums);

        for (int i = 0; i < 5; i++) {
            Song song = new Song(
                    "track" + (i + 1),
                    "title" + i,
                    "genre" + (i + 1),
                    2000 + (i + 1),
                    List.of(artistList.get(i))
            );
            song.setAlbum(albums.get(i));
            songList.add(song);
        }
        songRepository.saveAll(songList);
    }
}

