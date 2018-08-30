package ru.job4j.service;

import ru.job4j.model.Picture;

import java.util.List;

public interface PictureService {

    Picture findPicture(Long id);

    List<Picture> findPicsByPostId(Long id);
}
