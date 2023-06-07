package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.Batch;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BatchRepository extends JpaRepository<Batch, String>, BatchRepositoryExtension {
//    @Query("select u from User u where u.email=?1")
//    Optional<User> findByEmail(String email);

    @Query("select b from Batch b where b.published = ?1")
    List<Batch> findAllWithPublishStatus(Boolean isPublished);
}
