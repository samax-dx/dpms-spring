package com.akcl.dpms.svc_main.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;
import java.util.Optional;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Machine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineId;

    private String workloadType;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "machine_spec_id")
    private MachineSpec machineSpec;

    @Column(name = "machine_spec_id", insertable = false, updatable = false)
    private Long machineSpecId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true)
    private MachineState machineState;

    @Transient
    private Long machineStateId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private List<BatchProcess> batchProcesses;

    Long getMachineStateId() {
        return Optional.ofNullable(machineState).map(MachineState::getMachineStateId).orElse(machineStateId);
    }
}
