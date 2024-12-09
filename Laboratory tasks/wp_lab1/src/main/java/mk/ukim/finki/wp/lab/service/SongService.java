package mk.ukim.finki.wp.lab.service;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;

import java.util.List;

public interface SongService {
    List<Song> listSongs();

    Artist addArtistToSong(Artist artist, Song song);

    Song findByTrackId(String trackId);

    void saveSong(Song song);

    Song findById(Long songId);

    void deleteSong(Song song);

}
