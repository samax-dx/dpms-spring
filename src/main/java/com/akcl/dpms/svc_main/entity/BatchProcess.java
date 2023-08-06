package com.akcl.dpms.svc_main.entity;

import com.akcl.dpms.util.AttributeCoder;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Getter;
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchProcess implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchProcessId;

    private String processId;

    private Integer processOrder;

    private boolean finished = false;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "batchProcess", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<BatchProcessParameter> parameters = new ArrayList<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "batch_id")
    private Batch batch;

    @Column(name = "batch_id", insertable = false, updatable = false)
    private String batchId;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @Column(name = "machine_id", insertable = false, updatable = false)
    private Long machineId;

    @OneToMany(mappedBy = "batchProcess", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ProcessExecution> executions = new ArrayList<>();

    @OneToOne
    private ProcessExecution activeExecution;

    @Transient
    @JsonIgnore
    @Getter(AccessLevel.NONE)
    @Setter(AccessLevel.NONE)
    private AttributeCoder<BatchProcessParameter> attributeCoder = new AttributeCoder<BatchProcessParameter>("name", "value", "name", "batchProcessParameterId") {};

    @JsonAnyGetter
    public Map<String, Object> produceExtraProps() {
        Map<String, Object> extraProps = new HashMap<>();
        parameters.forEach(parameter -> attributeCoder.insertAttribute(parameter, extraProps));
        return extraProps;
    }

    @JsonAnySetter
    public void consumeExtraProp(String key, Object value) {
        attributeCoder.insertAttribute(key, value, parameters);
    }
}
