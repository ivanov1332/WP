package mk.ukim.finki.wp.lab.model;

import lombok.Data;

@Data
public class Artist {

    public long id;
    public String firstName;
    public String lastName;
    public String bio;

    public Artist(long id, String firstName, String lastName, String bio) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.bio = bio;
    }
}
