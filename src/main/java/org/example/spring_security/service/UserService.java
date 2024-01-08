package org.example.spring_security.service;

import jakarta.transaction.Transactional;
import org.example.spring_security.config.SecurityConfig;
import org.example.spring_security.model.Role;
import org.example.spring_security.model.User;
import org.example.spring_security.repository.RoleRepository;
import org.example.spring_security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;



    @Autowired
    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException {
        User user = userRepository.findByLogin(login);
        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        return user;
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @Transactional
    public void registerUser(String login, String password, String firstName, String lastName, int age, String email) {
        if (userRepository.findByLogin(login) != null) {
            throw new IllegalArgumentException("Login is already taken");
        }
        User user = new User();
        user.setLogin(login);
        user.setPassword(password);
        user.setName(firstName);
        user.setLastName(lastName);
        user.setAge(age);
        user.setEmail(email);
        Role userRole = roleRepository.findByName("USER");
        user.setRoles(Collections.singleton(userRole));
        userRepository.save(user);
    }

    @Transactional
    public void save(User user) {
        userRepository.save(user);
    }

    @Transactional
    public void deleteById(Long id) {
        userRepository.delete(findById(id));
    }

    @Transactional
    public void edit(Long id, User user) {
        User existingUser = findById(id);
        existingUser.setName(user.getName());
        existingUser.setLastName(user.getLastName());
        existingUser.setAge(user.getAge());
        existingUser.setEmail(user.getEmail());
        userRepository.save(user);
    }


    @Transactional
    public void addRole(Long id) {
        User user = findById(id);
        Role adminRole = roleRepository.findByName("ADMIN");
        user.getRoles().add(adminRole);
        save(user);
    }

    @Transactional
    public void deleteRole(Long id) {
        User user = findById(id);
        Role adminRole = roleRepository.findByName("ADMIN");
        user.getRoles().remove(adminRole);
        save(user);
    }
}
