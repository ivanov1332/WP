package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Album;
import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
import mk.ukim.finki.wp.lab.service.AlbumService;
import mk.ukim.finki.wp.lab.service.ArtistService;
import mk.ukim.finki.wp.lab.service.SongService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class SongController {

    private final SongService songService;
    private final ArtistService artistService;
    private final AlbumService albumService;

    public SongController(SongService songService, ArtistService artistService, AlbumService albumService) {
        this.songService = songService;
        this.artistService = artistService;
        this.albumService = albumService;
    }

    @GetMapping("/songs")
    public String getSongsPage(@RequestParam(required = false) String error, Model model) {
        if (error != null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", error);
        }
        List<Song> songs = songService.listSongs();
        songs.forEach(song -> System.out.println("Song: " + song + ", Album: " + song.getAlbum()));
        model.addAttribute("songs", songs);
        model.addAttribute("artists", artistService.listArtists());
        return "listSongs";
    }

    @PostMapping("/chooseSong")
    public String chooseSong(@RequestParam Long selectedSong, Model model) {
        Song song = songService.findById(selectedSong);

        if (song == null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "The selected song was not found.");
            return "redirect:/songs";
        }

        System.out.println("Song found " + song);
        List<Artist> artists = artistService.listArtists();

        model.addAttribute("SONG", song);
        model.addAttribute("artists", artists);

        return "songInfo";
    }

    @GetMapping("/songs/add")
    public String showAddSong(Model model) {
        List<Album> albums = albumService.findAll();
        model.addAttribute("albums", albums);
        return "addSong";
    }

    @PostMapping("/songs/add")
    public String saveSong(@RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genre,
                           @RequestParam int releaseYear,
                           @RequestParam Long albumId,
                           Model model) {

        Album album = albumService.findById(albumId);

        if (album == null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Album not found.");
            return "addSong";
        }

        Song song = new Song(title, trackId, genre, releaseYear, albumId);
        song.setAlbum(album);
        songService.saveSong(song);

        return "redirect:/songs";
    }

    @GetMapping("/songs/edit/{songId}")
    public String showEditForm(@PathVariable Long songId, Model model) {
        Song song = songService.findById(songId);

        if (song == null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "The selected song was not found.");
            return "redirect:/songs";
        }

        model.addAttribute("song", song);
        model.addAttribute("albums", albumService.findAll());
        return "editSong";
    }

    @PostMapping("/songs/edit/{songId}")
    public String editSong(@PathVariable Long songId,
                           @RequestParam String title,
                           @RequestParam String trackId,
                           @RequestParam String genre,
                           @RequestParam int releaseYear,
                           @RequestParam Long albumId,
                           Model model) {
        Song song = songService.findById(songId);

        if (song == null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Song not found.");
            return "redirect:/songs";
        }

        Album album = albumService.findById(albumId);

        if (album == null) {
            model.addAttribute("hasError", true);
            model.addAttribute("error", "Album not found");
            return "editSong";
        }

        song.setTitle(title);
        song.setTrackId(trackId);
        song.setGenre(genre);
        song.setReleaseYear(releaseYear);
        song.setAlbum(album);

        songService.saveSong(song);

        return "redirect:/songs";
    }

    @PostMapping("/songs/delete/{id}")
    public String deleteSong(@PathVariable Long id) {
        Song song = songService.findById(id);
        songService.deleteSong(song);
        return "redirect:/songs";
    }
}
