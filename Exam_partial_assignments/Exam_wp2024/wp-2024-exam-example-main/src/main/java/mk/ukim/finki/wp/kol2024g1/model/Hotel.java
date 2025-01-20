package mk.ukim.finki.wp.kol2024g1.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
public class Hotel {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Hotel(String name) {
        this.name = name;
    }
}
