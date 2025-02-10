package com.ex.dtos.newuser;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
public class NewUserDTO {

    @JsonProperty("customData")
    private CustomData customData;

    @JsonProperty("first_name")
    private String firstName;

    @JsonProperty("surname")
    private String surname;

    private String email;
    private String username;

    @JsonProperty("plain_password")
    private String plainPassword;
    private String roles;
}
