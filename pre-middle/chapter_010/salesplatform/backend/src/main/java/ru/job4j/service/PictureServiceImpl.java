package ru.job4j.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Picture;
import ru.job4j.repository.PictureRepository;

import java.util.List;

@Service("pictureServiceImpl")
@Transactional
public class PictureServiceImpl implements PictureService {

    private final PictureRepository repo;

    public PictureServiceImpl(final PictureRepository repo) {
        this.repo = repo;
    }

    @Override
    public Picture findPicture(final Long id) {
        return repo.findById(id)
            .orElseThrow(() -> new IllegalStateException("Picture not found"));
    }

    @Override
    public List<Picture> findPicsByPostId(final Long id) {
        return repo.findPicsByPostId(id);
    }
}
