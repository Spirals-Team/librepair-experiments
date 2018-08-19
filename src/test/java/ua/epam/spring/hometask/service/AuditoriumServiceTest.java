package ua.epam.spring.hometask.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import ua.epam.spring.hometask.config.AppConfig;
import ua.epam.spring.hometask.domain.Auditorium;
import ua.epam.spring.hometask.matcher.ModelMatcher;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by Vladimir on 13.10.2017.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(
        classes = AppConfig.class,
        loader = AnnotationConfigContextLoader.class
)
public class AuditoriumServiceTest {

    @Autowired
    AuditoriumService service;

    private Comparator<Auditorium> comparator = Comparator.comparing(Auditorium::getName)
            .thenComparing(Auditorium::getNumberOfSeats)
            .thenComparing(Comparator.comparing(a -> a.getVipSeats().toString()));

    private ModelMatcher<Auditorium> MATCHER = new ModelMatcher<>(comparator);

    @Test
    public void getAll() throws Exception {
        Auditorium auditorium1 = new Auditorium();
        auditorium1.setName("alpha");
        auditorium1.setNumberOfSeats(10);
        auditorium1.setVipSeats(Stream.of(1L, 2L, 3L).collect(Collectors.toSet()));

        Auditorium auditorium2 = new Auditorium();
        auditorium2.setName("beta");
        auditorium2.setNumberOfSeats(9);
        auditorium2.setVipSeats(Stream.of(5L).collect(Collectors.toSet()));

        Auditorium auditorium3 = new Auditorium();
        auditorium3.setName("gama");
        auditorium3.setNumberOfSeats(15);
        auditorium3.setVipSeats(Stream.of(1L, 10L, 15L).collect(Collectors.toSet()));


        MATCHER.assertCollectionEquals(
                Arrays.asList(auditorium1, auditorium2, auditorium3),
                service.getAll().stream().sorted(comparator).collect(Collectors.toList()));
    }

    @Test
    public void getByName() throws Exception {

        Auditorium auditorium2 = new Auditorium();
        auditorium2.setName("beta");
        auditorium2.setNumberOfSeats(9);
        auditorium2.setVipSeats(Stream.of(5L).collect(Collectors.toSet()));


        MATCHER.assertEquals(auditorium2, service.getByName("beta"));
    }


}