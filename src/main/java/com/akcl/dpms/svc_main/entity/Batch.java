package com.akcl.dpms.svc_main.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Batch implements Serializable {
    @Id
    private String batchId;

    private String buyerName;

    private String liquorRatio;

    private String styleId;

    private String orderId;

    private String pantone;

    private String labId;

    private double fabricsQuantity;

    private long totalRoll;

    private String challanNumber;

    private String fabricType;

    private String requiredGsm;

    private String bycybl;

    private String bodySLength;

    private String rycybl;

    private String ribSLength;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSS")
    @CreationTimestamp
    private LocalDateTime createdOn;

    @JsonIgnore
    @OneToMany(mappedBy = "batch", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<BatchProcess> batchProcesses;
}
