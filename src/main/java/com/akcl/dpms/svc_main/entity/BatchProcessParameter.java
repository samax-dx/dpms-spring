package com.akcl.dpms.svc_main.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PostLoad;
import javax.persistence.PostPersist;
import javax.persistence.PostUpdate;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Optional;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "batchProcessParameterId")
public class BatchProcessParameter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long batchProcessParameterId;

    private String name;

    private String value;

    @JsonIgnoreProperties({"parameters"})
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "batch_process_id")
    private BatchProcess batchProcess;

    @Transient
    private Long batchProcessId;

    @PostPersist
    @PostUpdate
    @PostLoad
    public void updateReferenceIds() {
        batchProcessId = Optional.ofNullable(batchProcess).map(BatchProcess::getBatchProcessId).orElse(null);
    }
}
