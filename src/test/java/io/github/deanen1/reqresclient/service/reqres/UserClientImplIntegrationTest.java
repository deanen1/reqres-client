package io.github.deanen1.reqresclient.service.reqres;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import java.util.List;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

@Tag("integration")
class UserClientImplIntegrationTest {

  private UserClientImpl client = new UserClientImpl("https://reqres.in");

  @Test
  void getUsersShouldReturnPageOne() {
    List<User> users = client.getUsers(1);

    assertThat(users).isNotNull();
    assertThat(users.size()).isEqualTo(6);

    assertUserEquals(users.get(0),
        1, "george.bluth@reqres.in", "George", "Bluth");
    assertUserEquals(users.get(1),
        2, "janet.weaver@reqres.in", "Janet", "Weaver");
    assertUserEquals(users.get(2),
        3, "emma.wong@reqres.in", "Emma", "Wong");
    assertUserEquals(users.get(3),
        4, "eve.holt@reqres.in", "Eve", "Holt");
    assertUserEquals(users.get(4),
        5, "charles.morris@reqres.in", "Charles", "Morris");
    assertUserEquals(users.get(5),
        6, "tracey.ramos@reqres.in", "Tracey", "Ramos");
  }

  void assertUserEquals(
      User user, int expectedId, String expectedEmail,
      String expectedFirstName, String expectedLastName) {
    assertThat(user.getId()).isEqualTo(expectedId);
    assertThat(user.getEmail()).isEqualTo(expectedEmail);
    assertThat(user.getFirstName()).isEqualTo(expectedFirstName);
    assertThat(user.getLastName()).isEqualTo(expectedLastName);
  }
}