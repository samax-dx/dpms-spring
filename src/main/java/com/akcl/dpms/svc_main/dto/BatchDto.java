package com.akcl.dpms.svc_main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchDto {
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
}
