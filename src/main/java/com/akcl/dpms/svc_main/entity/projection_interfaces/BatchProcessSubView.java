package com.akcl.dpms.svc_main.entity.projection_interfaces;

import com.akcl.dpms.svc_main.entity.ProcessExecution;
import com.akcl.dpms.util.AttributeCoder;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonIgnore;

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

    List<ProcessExecution> getExecutions();

    ProcessExecution getActiveExecution();

    MachineSubView getMachine();

    String getBatchId();

    @JsonAnyGetter
    default Map<String, Object> generateExtraProps() {
        HashMap<String, Object> extraProps = new HashMap<>();

        Long machineId = getMachine().getMachineId();
        extraProps.put("machineId", machineId);

        AttributeCoder<?> customAttributeCoder = new AttributeCoder<Object>("name", "value", "name", "batchProcessParameterId") {};
        extraProps.putAll(customAttributeCoder.readAttributes(getParameters()));

        return extraProps;
    }
}
