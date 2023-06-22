package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.Batch;
import com.akcl.dpms.svc_main.entity.BatchView;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.blazebit.persistence.spring.data.repository.EntityViewRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
@Transactional(readOnly = true)
public interface BatchViewRepository extends EntityViewRepository<BatchView, String> {
}
