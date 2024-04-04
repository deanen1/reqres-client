package io.github.deanen1.reqresclient.service.reqres;

import java.util.List;

public interface UserClient {

  List<User> getUsers(int page);

}
