package az.cybernet.service.user;

import az.cybernet.model.entity.user.User;


public interface UserService {
    User findByUsername(String username);

    User save(User user);
}
