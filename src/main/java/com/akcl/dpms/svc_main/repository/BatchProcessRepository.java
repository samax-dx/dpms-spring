package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.BatchProcess;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface BatchProcessRepository extends JpaRepository<BatchProcess, Long> {
//    @Query("select u from User u where u.email=?1")
//    Optional<User> findByEmail(String email);
}
