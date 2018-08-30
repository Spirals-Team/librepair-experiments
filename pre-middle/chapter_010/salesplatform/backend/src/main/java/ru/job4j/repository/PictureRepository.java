package ru.job4j.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.job4j.model.Picture;

import java.util.List;

public interface PictureRepository extends JpaRepository<Picture, Long> {

    /**
     * Find all pics related to post
     *
     * @param postId postId
     * @return List<Picture>
     */
    List<Picture> findPicsByPostId(final Long postId);
}
