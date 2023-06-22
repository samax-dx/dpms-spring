package com.akcl.dpms.svc_main.entity.projection_interfaces;

import com.akcl.dpms.util.AttributeCoder;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public interface BatchProcessSubView {
    Long getBatchProcessId();

    String getProcessId();

    Integer getProcessOrder();

    boolean getFinished();

    @JsonIgnore
    List<BatchProcessParameterSubView> getParameters();

    MachineSubView getMachine();

    String getBatchId();

    @JsonAnyGetter
    default Map<String, Object> generateExtraProps() {
        AttributeCoder<Map<String, Object>> attributeCoder = new AttributeCoder<Map<String, Object>>("name", "value", "name", "batchProcessParameterId") {};
        return attributeCoder.readAttributes(getParameters());
    }
}
