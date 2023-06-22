package com.akcl.dpms.svc_main.entity.projection_interfaces;


public interface MachineSubView {
    Long getMachineId();

    String getWorkloadType();

    Long getMachineSpecId();

    Long getMachineStateId();
}
