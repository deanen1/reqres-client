package io.github.deanen1.reqresclient.service.reqres;

import java.util.List;
import io.netty.channel.ChannelOption;
import io.netty.resolver.DefaultAddressResolverGroup;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Service
public class UserClientImpl implements UserClient {

  private static final String PAGE_QUERY_PARAM = "page";

  private WebClient webClient;

  public UserClientImpl(@Value("${reqres.users.url}") String url) {
    HttpClient client = HttpClient
        .create()
        .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
        .resolver(DefaultAddressResolverGroup.INSTANCE);

    webClient = WebClient
        .builder()
        .baseUrl(url)
        .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE)
        .clientConnector(new ReactorClientHttpConnector(client))
        .build();
  }

  @Override
  public List<User> getUsers(int page) {
    UserServiceResponse response = webClient.get()
        .uri(uriBuilder -> uriBuilder
            .path("/api/users")
            .queryParam(PAGE_QUERY_PARAM, String.valueOf(page))
            .build())
        .retrieve()
        .onStatus(
            HttpStatus::is5xxServerError,
            clientResponse -> Mono.error(new UserServiceException("Server error")))
        .bodyToMono(UserServiceResponse.class)
        .block();

    return response.getUsers();
  }
}
