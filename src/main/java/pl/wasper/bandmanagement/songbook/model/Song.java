package pl.wasper.bandmanagement.songbook.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "song")
@Getter
@Setter
public class Song {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "tempo")
    private int tempo;

    @Column(name = "tone")
    private String tone;

    @Column(name = "info")
    private String info;

    @Column(name = "content")
    private String content;

    @ManyToMany
    @JoinTable(name = "song_tag",
        joinColumns = @JoinColumn(name = "song_id", referencedColumnName = "id"),
        inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private List<Tag> tags;
}
