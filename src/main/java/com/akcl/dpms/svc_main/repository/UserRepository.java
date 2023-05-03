package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u where u.email=?1")
    Optional<User> findByEmail(String email);
}
