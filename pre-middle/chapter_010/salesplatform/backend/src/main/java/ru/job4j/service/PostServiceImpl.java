package ru.job4j.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.model.Picture;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.model.car.Car;
import ru.job4j.repository.PictureRepository;
import ru.job4j.repository.PostRepository;
import ru.job4j.repository.car.CarRepository;

import javax.naming.AuthenticationException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Service("postServiceImpl")
@Transactional
public class PostServiceImpl implements PostService {

    private final PostRepository postRepo;
    private final CarRepository carRepo;
    private final PictureRepository picRepo;

    public PostServiceImpl(final PostRepository postRepo, final CarRepository carRepo,
                           final PictureRepository picRepo) {
        this.postRepo = postRepo;
        this.carRepo = carRepo;
        this.picRepo = picRepo;
    }

    @Override
    public Long addPostAd(final User user, final Post post, final List<byte[]> pics) {
        post.setPublishDate(Timestamp.valueOf(LocalDateTime.now()));
        post.setUser(user);
        Long carId = carRepo.save(post.getCar()).getId();
        post.setCar(new Car(carId));
        Post publishedPost = postRepo.save(post);
        pics.forEach(pic ->
            picRepo.save(new Picture(pic, publishedPost)));
        return publishedPost.getId();
    }

    @Override
    public void editPostAd(final User user, final Post post) throws AuthenticationException {
        post.setPictures(picRepo.findPicsByPostId(post.getId()));
        if (!user.getId().equals(post.getUser().getId())) {
            throw new AuthenticationException(
                String.format("User: %s attempts to edit not his own post.", user.getEmail()));
        }
        postRepo.save(post);
    }

    @Override
    public List<Post> findAll() {
        return postRepo.findAll();
    }

    @Override
    public List<Post> findTodaysPosts() {
        return postRepo.findAllByPublishDateToday();
    }

    @Override
    public List<Post> findWithPics() {
        return postRepo.findAllByPicsIsNotNull();
    }

    @Override
    public List<Post> findByCarManufacture(final String manufacture) {
        return postRepo.findAllByCarManufacture(manufacture);
    }
}
