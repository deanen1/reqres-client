package io.github.deanen1.reqresclient;

import java.util.List;
import io.github.deanen1.reqresclient.service.reqres.User;
import io.github.deanen1.reqresclient.service.reqres.UserClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ReqresClientApplication implements CommandLineRunner {
  @Autowired
  private UserClient userClient;

  public static void main(String[] args) {
    SpringApplication.run(ReqresClientApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {
    List<User> users = userClient.getUsers(1);

    System.out.println("*".repeat(43));
    System.out.println("Users");
    System.out.println("*".repeat(43));
    users.stream()
        .forEach(u -> print(u));
  }

  private void print(User u) {
    System.out.printf(
        "%2s|%24s|%7s|%6s|%n",
        String.valueOf(u.getId()), u.getEmail(), u.getFirstName(), u.getLastName()
    );
  }
}
