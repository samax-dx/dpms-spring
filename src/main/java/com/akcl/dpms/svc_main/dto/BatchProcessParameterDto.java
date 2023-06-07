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
public class BatchProcessParameterDto {
    private Long batchProcessParameterId;

    private String name;

    private String value;

    private Long batchProcessId;
}
