package mk.ukim.finki.wp.lab.service.implementation;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.repository.SongRepository;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SongServiceImplementation implements SongService {

    SongRepository songRepository;

    SongServiceImplementation(SongRepository songRepository) {
        this.songRepository = songRepository;
    }

    @Override
    public List<Song> listSongs() {
        return songRepository.findAll();
    }

    @Override
    public Artist addArtistToSong(Artist artist, Song song) {
        if (artist == null || song == null) return null;

        List<Artist> performers = song.getPerformers();
        if (performers == null || !(performers instanceof ArrayList)) {
            performers = new ArrayList<>(performers != null ? performers : List.of());
            song.setPerformers(performers);
        }

        performers.add(artist);
        songRepository.save(song);
        return artist;
    }


    @Override
    public Song findByTrackId(String trackId) {
        return songRepository.findByTrackId(trackId);
    }

    @Override
    public void saveSong(Song song) {
        songRepository.save(song);
    }

    @Override
    public Song findById(Long songId) {
        return songRepository.findById(songId).orElse(null);
    }

    public List<Song> findByAlbum(Long albumId) {
        return songRepository.findAllByAlbum_Id(albumId);
    }

    @Override
    public void deleteSong(Song song) {
        songRepository.delete(song);
    }

}
