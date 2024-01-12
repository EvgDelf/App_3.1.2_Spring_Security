package org.example.spring_security.repository;

import org.example.spring_security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    User findByUsername(String username);

    User findByLogin(@Param ("login") String login);

    @Query("select u from user u where u.login=:login and u.password=:password")
    User findByLoginAndPassword(@Param("login") String login,@Param("password") String password);
}
