package pl.wasper.bandmanagement.songbook.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.wasper.bandmanagement.songbook.model.Song;

@Repository
public interface SongRepository extends JpaRepository<Song, Long> {
}
