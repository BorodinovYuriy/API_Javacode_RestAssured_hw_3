package com.ex.dtos.newuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class CustomData {

    @JsonProperty("isCV")
    private Boolean isCV;

    @JsonProperty("salesOpenTime")
    private String salesOpenTime;

    @JsonProperty("salesStatus")
    private String salesStatus;


}

