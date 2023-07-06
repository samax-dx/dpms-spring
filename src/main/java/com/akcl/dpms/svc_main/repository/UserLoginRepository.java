package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.models.UserLogin;
import com.akcl.dpms.util.ZViewEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.Repository;

import java.util.Optional;


@org.springframework.stereotype.Repository
public interface UserLoginRepository extends Repository<ZViewEntity, Long> {
    String USER_LOGIN_QUERY = "select New com.akcl.dpms.svc_main.models.UserLogin(u.userId, u.name, u.email, l.loginId) from User u join Login l on u.userId = l.user.userId";

    @Query(USER_LOGIN_QUERY + " where l.loginId=?1")
    Optional<UserLogin> findUserLoginByLoginId(String loginId);
}
