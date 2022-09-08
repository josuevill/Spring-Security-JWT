package io.gateways.userservice.services;

import io.gateways.userservice.entities.Role;
import io.gateways.userservice.entities.User;

import java.util.List;

public interface IUserService {
    List<User>getUsers();
    User getUser(String username);
    User saveUser(User user);
    Role saveRole(Role role);
    void addUserToRole(String username, String roleName);
}
