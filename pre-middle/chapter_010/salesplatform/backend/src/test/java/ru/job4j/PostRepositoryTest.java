package ru.job4j;

import org.junit.Ignore;
import org.junit.Test;
import ru.job4j.model.Post;
import ru.job4j.model.User;
import ru.job4j.model.car.Car;

public class PostRepositoryTest {

    private User user;
    private Car car;
    private Post post;

    //    @Before
//    public void setUp() {
//        user = new User()
//            .setEmail("petrov@email.com")
//            .setPassw("1234")
//            .setRole(new Role(1L, "USER"))
//            .setPhone("555-55-55");
//        user.setId(new UserDaoImpl().create(user));
//        car = new Car()
//            .setBody(new Body().setId(2L))
//            .setCategory(new Category().setId(2L))
//            .setFuel(new Fuel().setId(2L))
//            .setCarModel(new CarModel().setId(2L))
//            .setTransmission(new Transmission().setId(2L))
//            .setEngineSize(1.2)
//            .setMileage(4000L)
//            .setYear(2018);
//        car.setId(new CarRepository().create(car));
//        car = new CarRepository().findOne(car.getId());
//        post = new Post()
//            .setContent("description")
//            .setPrice(35000D)
//            .setIsActive(Boolean.TRUE)
//            .setCar(car)
//            .setUser(user);
//        post.setId(new PostRepository().create(post));
//        post = new PostRepository().findOne(post.getId());
//    }
//
    @Test
    @Ignore
    public void findWithPics() {
//        assertThat(new PostRepository().findWithPics().size(), greaterThan(3));
    }
//
//    @Test
//    @Ignore
//    public void findTodaysPosts() {
//        assertThat(new PostRepository().findTodaysPosts().size(), equalTo(2));
//    }
//
//    @Test
//    public void findByCarManufacture() {
//        List<Post> posts = new PostRepository().findByCarManufacture("BMW");
//        assertThat(posts.size(), greaterThanOrEqualTo(1));
//        assertThat(posts.get(0).getCar().getCarModel().getManufacture().getName(),
//            equalTo("BMW"));
//    }

    @Test
    @Ignore
    public void findPicsByPostId() {
    }

    @Test
    @Ignore
    public void addPostAd() {
    }

    @Test
    @Ignore
    public void editPostAd() {
    }
}