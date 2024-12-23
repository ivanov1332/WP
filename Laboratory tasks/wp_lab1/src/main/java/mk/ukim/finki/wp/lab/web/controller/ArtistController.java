package mk.ukim.finki.wp.lab.web.controller;

import mk.ukim.finki.wp.lab.model.Artist;
import mk.ukim.finki.wp.lab.model.Song;
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
public class ArtistController {
    ArtistService artistService;
    SongService songService;

    public ArtistController(ArtistService artistService, SongService songService) {
        this.artistService = artistService;
        this.songService = songService;
    }

    @GetMapping("/artist")
    public String listArtists(@RequestParam(required = false) String trackId, Model model){
        List<Artist> artists = artistService.listArtists();
        model.addAttribute("artists", artists);
        model.addAttribute("trackId", trackId);
        return "artistsList";
    }

    @PostMapping("track/assignArtist")
    public String assignArtist(@RequestParam(required = false) Long trackId,@RequestParam Long artistId,Model model){

        artistService.assignArtistToTrack(trackId, artistId);
        return "redirect:/songs/" + trackId;
    }

    @GetMapping("/songs/{trackId}")
    public String getSongDetails(@PathVariable Long trackId, Model model){
        Song song = songService.findById(trackId);
        List<Artist> artists = artistService.listArtists();
        model.addAttribute("song", song);
        model.addAttribute("artists", artists);
        return "songDetails";
    }
}