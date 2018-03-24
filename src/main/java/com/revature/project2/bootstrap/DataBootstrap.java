package com.revature.project2.bootstrap;

import com.revature.project2.helpers.Seeder;
import com.revature.project2.users.User;
import com.revature.project2.users.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;

@Slf4j
@Component
public class DataBootstrap implements ApplicationListener<ContextRefreshedEvent> {

  private UserService userService;
  private Seeder seeder;

  @Autowired
  public DataBootstrap(UserService userService, Seeder seeder) {
    this.userService = userService;
    this.seeder = seeder;
  }

  @Override
  public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
    createAdmin();
    createUsers(5);
    createEvents(20);
  }

  private void createAdmin() {
    if (userService.findByUsername("admin") == null) {
      User admin = seeder.makeAdmin();
      log.info("Created basic Administrator");
      userService.save(admin);
    }
  }

  private void createUsers(int qty) {
    IntStream.range(0, qty).forEach(i -> {
      User u = seeder.makeUser();
      userService.save(u);
    });
  }

  private void createEvents(int qty) {
    AtomicInteger numOfUsers = new AtomicInteger();
    userService.findAllUsers().forEach(i -> numOfUsers.getAndIncrement());
    seeder.createEvents(qty, numOfUsers.get());
  }
}
