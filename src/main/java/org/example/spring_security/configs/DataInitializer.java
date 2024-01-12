package org.example.spring_security.configs;

import org.example.spring_security.model.Role;
import org.example.spring_security.model.User;
import org.example.spring_security.repository.RoleRepository;
import org.example.spring_security.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.util.Collections;
import java.util.LinkedList;
import java.util.stream.Stream;

@Component
public class DataInitializer implements CommandLineRunner {
    private final RoleRepository roleRepository;

    private final UserRepository userRepository;

    public DataInitializer(RoleRepository roleRepository, UserRepository userRepository) {
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @Override
    public void run(String... args) {

        if (roleRepository.count() == 0) {

            Role adminRole = new Role();
            adminRole.setName("ROLE_ADMIN");
            roleRepository.save(adminRole);

            Role userRole = new Role();
            userRole.setName("ROLE_USER");
            roleRepository.save(userRole);

            User user = new User();
            user.setLogin("admin");
            user.setUsername("admin");
            user.setLastName("admin");
            user.setPassword("admin");
            user.setAge(44);
            user.setEmail("asd@asd.com");
            user.setRoles(Stream.of(adminRole).toList());
            userRepository.save(user);
        }
    }
}
