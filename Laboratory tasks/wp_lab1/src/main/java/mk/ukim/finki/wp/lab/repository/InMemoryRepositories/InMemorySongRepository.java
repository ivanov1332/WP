package mk.ukim.finki.wp.lab.repository.InMemoryRepositories;
import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Song;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class InMemorySongRepository {

    public List<Song> findAll() {
        return DataHolder.songList;
    }

    public Song findByTrackId(String trackId) {
        return DataHolder.songList.stream().filter(song -> song.getTrackId().equals(trackId)).findFirst().orElse(null);
    }

    public Song findById(Long id) {
        return DataHolder.songList.stream()
                .filter(song -> song.getId() != null && song.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public Song save(Song song) {
        DataHolder.songList.removeIf(s -> s.getTrackId().equals(song.getTrackId()));
        DataHolder.songList.add(song);
        return song;
    }

    public void delete(Song song) {
        DataHolder.songList.removeIf(s -> s.getTrackId().equals(song.getTrackId()));
    }


}
