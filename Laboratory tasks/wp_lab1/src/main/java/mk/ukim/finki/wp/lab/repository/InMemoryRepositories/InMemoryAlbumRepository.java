package mk.ukim.finki.wp.lab.repository.InMemoryRepositories;

import mk.ukim.finki.wp.lab.bootstrap.DataHolder;
import mk.ukim.finki.wp.lab.model.Album;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public class InMemoryAlbumRepository {

    public List<Album> findAll() {
        return DataHolder.albums;
    }

    public Optional<Album> findById(Long id) {
        return DataHolder.albums.stream().filter(album -> album.getId().equals(id)).findFirst();
    }

    public Album save(Album album) {
        DataHolder.albums.removeIf(album1 -> album1.getId().equals(album.getId()));
        DataHolder.albums.add(album);
        return album;
    }
}
