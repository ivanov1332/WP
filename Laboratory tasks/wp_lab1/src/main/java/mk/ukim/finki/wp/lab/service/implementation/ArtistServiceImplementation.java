package mk.ukim.finki.wp.lab.service.implementation;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.ArtistRepository;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.ArtistService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArtistServiceImplementation implements ArtistService {

    ArtistRepository artistRepository;
    SongRepository songRepository;

    public ArtistServiceImplementation(ArtistRepository artistRepository,SongRepository songRepository) {
        this.artistRepository = artistRepository;
        this.songRepository = songRepository;
    }

    @Override
    public List<Artist> listArtists() {
        return artistRepository.findAll();
    }

    @Override
    public Artist findById(Long id) {
        return artistRepository.findById(id).orElse(null);
    }

    @Override
    public void assignArtistToTrack(Long trackId, Long artistId) {
        Song song = songRepository.findById(trackId)
                .orElseThrow(() -> new RuntimeException("Song not found with id: " + trackId));
        Artist artist = artistRepository.findById(artistId)
                .orElseThrow(() -> new RuntimeException("Artist not found with id: " + artistId));

        if (!song.getPerformers().contains(artist)) {
            song.getPerformers().add(artist);
        }

        songRepository.save(song);
    }


}
