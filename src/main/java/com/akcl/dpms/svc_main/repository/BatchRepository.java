package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.Batch;
import com.akcl.dpms.svc_main.entity.projection_interfaces.BatchView;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public interface BatchRepository extends JpaRepository<Batch, String> {
    @Override
    @NonNull
    default List<Batch> findAll() {
        return findAll(Sort.by("batchId"));
    }

    @Query("select b from Batch b where b.published = ?1")
    List<BatchView> findAllWithPublishStatus(Boolean isPublished);

    @Query("select b from Batch b where b.batchId = ?1")
    Optional<BatchView> findOneWithId(String batchId);
}
