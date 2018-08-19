package ua.epam.spring.hometask.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ua.epam.spring.hometask.domain.Ticket;
import ua.epam.spring.hometask.domain.User;
import ua.epam.spring.hometask.service.UserService;

import java.time.LocalDateTime;
import java.util.Random;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Created by Vladimir on 22.10.2017.
 */
@Aspect
@Component
public class LuckyWinnerAspect {

    private Random random = new Random();

    @Autowired
    UserService userService;

    @Pointcut("execution(* ua.epam.spring.hometask.service.BookingService.bookTickets(..))")
    private void bookTickets() {
    }

    @Around("bookTickets() && args(tickets)")
    public void tryCatchLuck(ProceedingJoinPoint jp, Set<Ticket> tickets) throws Throwable {
        if (checkLucky()) {
            Set<User> users = tickets.stream().map(Ticket::getUser).collect(Collectors.toSet());
            users.forEach(user -> {
                user.getLuckyEvents().add(LocalDateTime.now());
                userService.save(user);
            });
        }
        jp.proceed(new Object[]{tickets});
    }

    private boolean checkLucky() {
        return random.nextBoolean();
    }

    public void printStatistics() {

        userService.getAll().forEach(user -> {
            System.out.println(user.toString());
            System.out.println("Lucky events are happened:");
            user.getLuckyEvents().forEach(System.out::println);
        });

    }

}
