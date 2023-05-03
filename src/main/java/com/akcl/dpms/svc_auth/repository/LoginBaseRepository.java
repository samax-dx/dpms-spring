package com.akcl.dpms.svc_auth.repository;

import com.akcl.dpms.svc_auth.entity.LoginBase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface LoginBaseRepository extends JpaRepository<LoginBase, String> {
}
