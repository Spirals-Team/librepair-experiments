package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.job4j.model.Post;

import java.util.List;


public interface PostRepository extends JpaRepository<Post, Long> {

    /**
     * Find all today's posts
     *
     * @return List<Post>
     */
    @Query("SELECT p FROM Post p WHERE p.publishDate >= CURRENT_DATE")
    List<Post> findAllByPublishDateToday();

    /**
     * Find all posts with pics
     *
     * @return List<Post>
     */
    @Query("SELECT p FROM Post p LEFT JOIN p.pictures pic WHERE pic.id IS NOT NULL")
    List<Post> findAllByPicsIsNotNull();

    /**
     * Find all posts by car manufacture
     *
     * @param manufacture manufacture
     * @return List<Post>
     */
    @Query("SELECT p from Post p join p.car c join c.carModel cm join cm.manufacture m where m.name = ?1")
    List<Post> findAllByCarManufacture(String manufacture);
}
