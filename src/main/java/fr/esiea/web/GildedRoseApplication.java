package fr.esiea.web;

import fr.esiea.models.GildedRose;
import fr.esiea.models.Item;
import fr.esiea.services.DatabaseAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Sample web application.<br/>
 * Run {@link #main(String[])} to launch.
 */
@SpringBootApplication
@RestController
@ComponentScan(basePackages = {"fr.esiea.controllers"})
public class GildedRoseApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(GildedRoseApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(GildedRoseApplication.class);
        Runnable updateQualityTask = () -> {
            String threadName = Thread.currentThread().getName();
            DatabaseAdapter da = DatabaseAdapter.getInstance();
            List<Item> listItems = da.getItems();
            GildedRose gr = new GildedRose(listItems.toArray(new Item[listItems.size()]));
            gr.updateQuality();
            da.setItems(gr.getItems());
            LOGGER.info("updateQuality with " + threadName);
        };
        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);
        scheduledExecutorService.scheduleAtFixedRate(updateQualityTask, 0L, 15L, TimeUnit.MINUTES);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            LOGGER.info("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                LOGGER.info(beanName);
            }

        };
    }
}
