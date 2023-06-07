package com.akcl.dpms.svc_main.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class BatchProcessDto {
    private Long batchProcessId;

    private String processId;

    private Integer processOrder;

    private String batchId;

    List<BatchProcessParameterDto> processParameters;
}
