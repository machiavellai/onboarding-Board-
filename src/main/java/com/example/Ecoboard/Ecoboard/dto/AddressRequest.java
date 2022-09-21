package com.example.Ecoboard.Ecoboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class AddressRequest {

    private String permanentAddress;

    private String presentAddress;

    private String presentTelephone_no;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    private String presentNationality;

    private String  Sex;

    private int height;

    private int weight;

    private String MaritalStatus;

}
