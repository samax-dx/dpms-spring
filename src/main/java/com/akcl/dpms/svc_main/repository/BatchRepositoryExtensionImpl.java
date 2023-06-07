package com.akcl.dpms.svc_main.repository;

import com.akcl.dpms.svc_main.entity.Batch;
import org.springframework.data.domain.Sort;

import java.util.List;

public abstract class BatchRepositoryExtensionImpl implements BatchRepositoryExtension {
    @Override
    public List<Batch> findAll() {
        return findAll(Sort.by(Sort.Direction.DESC, "processOrder"));
    }
}
