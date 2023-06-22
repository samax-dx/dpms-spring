package com.akcl.dpms.svc_main.entity;

import com.blazebit.persistence.view.EntityView;
import com.blazebit.persistence.view.IdMapping;
import com.blazebit.persistence.view.Mapping;

import java.util.List;


@EntityView(BatchProcess.class)
public interface BatchProcessSubView {
    @IdMapping
    Long getBatchProcessId();

    String getProcessId();

    Integer getProcessOrder();

    boolean getFinished();

    @Mapping("batch.batchId")
    String getBatchId();

    List<BatchProcessParameterSubView> getParameters();
//
//    @JsonIgnoreProperties({"batchProcesses"})
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "batch_id")
//    private Batch batch;

//    @JsonIgnoreProperties({"batchProcesses"})
//    @ManyToOne(fetch = FetchType.EAGER)
//    @JoinColumn(name = "machine_id")
//    private Machine machine;
//
//    @Transient
//    private Long machineId;
//
//    @PostPersist
//    @PostUpdate
//    @PostLoad
//    public void updateReferenceIds() {
//        batchId = Optional.ofNullable(batch).map(Batch::getBatchId).orElse(null);
//        machineId = Optional.ofNullable(machine).map(Machine::getMachineId).orElse(null);
//    }
//
//    @Transient
//    @JsonIgnore
//    @Getter(AccessLevel.NONE)
//    @Setter(AccessLevel.NONE)
//    private AttributeCoder<BatchProcessParameter> attributeCoder = new AttributeCoder<BatchProcessParameter>("name", "value", "name", "batchProcessParameterId") {};
//
//    @JsonAnyGetter
//    public Map<String, Object> produceExtraProps() {
//        Map<String, Object> extraProps = new HashMap<>();
//        parameters.forEach(parameter -> attributeCoder.insertAttribute(parameter, extraProps));
//        return extraProps;
//    }
//
//    @JsonAnySetter
//    public void consumeExtraProp(String key, Object value) {
//        attributeCoder.insertAttribute(key, value, parameters);
//    }
}
