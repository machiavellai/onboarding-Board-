package com.example.Ecoboard.Ecoboard.dto;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PersonResponse {
   
    private String email;
    private String message;
}
