package io.gateways.userservice.services;

import io.gateways.userservice.entities.Role;
import io.gateways.userservice.entities.User;
import io.gateways.userservice.repository.RoleRepository;
import io.gateways.userservice.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService implements IUserService, UserDetailsService {

    private final UserRepository UserRepository;
    private final RoleRepository RoleRepository;
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    public List<User> getUsers() {
        log.info("Mostrar usuarios");
        return UserRepository.findAll();
    }

    @Override
    public User getUser(String username) {
        log.info("Mostrar usuario {}", username);
        return UserRepository.findByUsername(username);
    }

    @Override
    public User saveUser(User user) {
        log.info("Agregando un nuevo usuario {} a la base de datos", user.getName());
        user.setPassword(passwordEncoder().encode(user.getPassword()));
        return UserRepository.save(user);
    }

    @Override
    public Role saveRole(Role role) {
        log.info("Agregando el rol {} a la base de datos", role.getName());
        return RoleRepository.save(role);
    }

    @Override
    public void addUserToRole(String username, String roleName) {
        log.info("Agregando rol {} al usuario", roleName, username);
        User user = UserRepository.findByUsername(username);
        Role role = RoleRepository.findByName(roleName);
        user.getRoles().add(role);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = UserRepository.findByUsername(username);
        if (user == null) {
            log.error("El usuario no se encuentra en la base de datos");
            throw new UsernameNotFoundException("El usuario no se encuentra en la base de datos");
        } else {
            log.info("El usuario no se encuentra en la base de datos: {}", username);
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role ->  authorities.add(new SimpleGrantedAuthority(role.getName())));

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), authorities);
    }
}
