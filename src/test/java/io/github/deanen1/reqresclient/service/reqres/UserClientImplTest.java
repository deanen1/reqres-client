package io.github.deanen1.reqresclient.service.reqres;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.maciejwalkowiak.wiremock.spring.ConfigureWireMock;
import com.maciejwalkowiak.wiremock.spring.EnableWireMock;
import com.maciejwalkowiak.wiremock.spring.InjectWireMock;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@EnableWireMock({
    @ConfigureWireMock(name = "user-service", port = 9099)
})
public class UserClientImplTest {

  @InjectWireMock("user-service")
  private WireMockServer wiremock;

  private UserClientImpl client = new UserClientImpl("http://localhost:9099");

  @Test
  void getUsersShouldReturnPageOne() throws Exception {
    wiremock.setScenarioState("Get Users", "Success");

    List<User> users = client.getUsers(1);

    assertThat(users).isNotNull();
    assertThat(users.size()).isEqualTo(2);
  }

  @Test
  void getUsersShouldThrowUserServiceExceptionOnHttp500() throws Exception {
    wiremock.setScenarioState("Get Users", "Error");

    assertThrows(
        UserServiceException.class,
        () -> client.getUsers(1)
    );
  }
}
