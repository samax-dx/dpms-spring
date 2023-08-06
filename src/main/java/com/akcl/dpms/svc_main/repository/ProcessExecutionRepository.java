package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.ProcessExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ProcessExecutionRepository extends JpaRepository<ProcessExecution, Long> {
//    @Query("select u from User u where u.email=?1")
//    Optional<User> findByEmail(String email);
}
