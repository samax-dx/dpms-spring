package com.akcl.dpms.svc_main.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "machineId")
public class Machine implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineId;

    private String workloadType;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "machine_spec_id")
    private MachineSpec machineSpec;

    @Transient
    private Long machineSpecId;

    @OneToOne(mappedBy = "machine", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private MachineState machineState;

    @Transient
    private Long machineStateId;

    @JsonIgnore
    @OneToMany(mappedBy = "machine", cascade = CascadeType.ALL)
    private List<BatchProcess> batchProcesses;

    @PostPersist
    @PostUpdate
    @PostLoad
    public void updateReferenceIds() {
        machineSpecId = Optional.ofNullable(machineSpec).map(MachineSpec::getMachineSpecId).orElse(null);
        machineStateId = Optional.ofNullable(machineState).map(MachineState::getMachineStateId).orElse(null);
    }
}
