package com.akcl.dpms.svc_main.entity;

import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "machineSpecId")
public class MachineSpec implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long machineSpecId;

    private String machineName;

    private String modelNumber;

    private String taskType;

    @JsonIgnore
    @OneToMany(mappedBy = "machineSpec", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MachineSpecAttribute> attributes;

    @JsonIgnore
    @OneToMany(mappedBy = "machineSpec", cascade = CascadeType.ALL)
    List<Machine> machines;

    @Transient
    @JsonIgnore
    @JsonAnySetter
    @JsonAnyGetter
    private Map<String, Object> extraProps = new HashMap<>();
}
