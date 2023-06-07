package com.akcl.dpms.svc_main.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
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
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "batchProcessId")
//@Table(uniqueConstraints = { @UniqueConstraint(columnNames = {"process_id", "batch_id"}) })
public class BatchProcess implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchProcessId;

    private String processId;

    private Integer processOrder;

    private boolean finished = false;

    @JsonIgnore
    @OneToMany(mappedBy = "batchProcess", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchProcessParameter> parameters;

    @JsonIgnoreProperties({"batchProcesses"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Transient
    private String batchId;

    @JsonIgnoreProperties({"batchProcesses"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Transient
    private Long machineId;

    @Transient
    @JsonIgnore
    @JsonAnySetter
    @JsonAnyGetter
    private Map<String, Object> extraProps = new HashMap<>();

    @PostPersist
    @PostUpdate
    @PostLoad
    public void updateReferenceIds() {
        batchId = Optional.ofNullable(batch).map(Batch::getBatchId).orElse(null);
        machineId = Optional.ofNullable(machine).map(Machine::getMachineId).orElse(null);
    }
}
