package org.example.spring_security.repository;

import org.example.spring_security.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {

    @Query("select r from role r where r.name=:name")
    Role findByName(String name);
}
