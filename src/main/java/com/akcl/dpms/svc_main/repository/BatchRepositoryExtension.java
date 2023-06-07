package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.Batch;
import org.springframework.data.domain.Sort;

import java.util.List;


public interface BatchRepositoryExtension {
    List<Batch> findAll(Sort sort);

    List<Batch> findAll();
}
