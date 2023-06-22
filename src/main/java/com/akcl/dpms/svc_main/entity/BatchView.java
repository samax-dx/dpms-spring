package com.akcl.dpms.svc_main.entity;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import java.time.LocalDateTime;
import java.util.List;


@EntityView(Batch.class)
public interface BatchView {
    @IdMapping
    String getBatchId();

    String getBuyerName();

    String getLiquorRatio();

    String getStyleId();

    String getOrderId();

    String getPantone();

    String getLabId();

    double getFabricsQuantity();

    long getTotalRoll();

    String getChallanNumber();

    String getFabricType();

    String getRequiredGsm();

    String getBycybl();

    String getBodySxLength();

    String getRycybl();

    String getRibSxLength();

    Boolean getPublished();

    Boolean getFinished();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime getUpdatedOn();

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    LocalDateTime getCreatedOn();

    List<BatchProcessSubView> getBatchProcesses();

//    @Mapping(fetches = "batchExecutionOrder")
//    BatchExecutionOrder getBatchExecutionOrder();
}
