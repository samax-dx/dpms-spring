package com.akcl.dpms.svc_main.entity;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;


@EntityView(BatchProcessParameter.class)
public interface BatchProcessParameterSubView {
    @IdMapping
    Long getBatchProcessParameterId();

    String getName();

    String getValue();
}
