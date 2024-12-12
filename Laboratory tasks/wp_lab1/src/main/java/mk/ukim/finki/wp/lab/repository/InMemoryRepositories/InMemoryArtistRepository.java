package mk.ukim.finki.wp.lab.repository.InMemoryRepositories;
import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryArtistRepository {

    public List<Artist> findAll() {
        return DataHolder.artistList;
    }

    public Optional<Artist> findById(Long id) {
        return DataHolder.artistList.stream().filter(artist -> artist.getId() == id).findFirst();
    }

    public Optional<Song> findSongById(Long trackId) {
        return DataHolder.songList.stream()
                .filter(song -> song.getId().equals(trackId))
                .findFirst();
    }

    public void assignArtistToTrack(Long trackId, Long artistId) {
        Optional<Song> songOptional = findSongById(trackId);
        Optional<Artist> artistOptional = findById(artistId);

        if (songOptional.isPresent() && artistOptional.isPresent()) {
            Song song = songOptional.get();
            Artist artist = artistOptional.get();
            song.setArtist(artist);
        }
    }

}
