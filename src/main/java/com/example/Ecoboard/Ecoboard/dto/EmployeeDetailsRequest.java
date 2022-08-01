package com.example.Ecoboard.Ecoboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;


import javax.persistence.Column;
import java.util.Date;

@Data
public class EmployeeDetailsRequest {
    @Column(name = "family_Name")
    private String familyName;

    private String fullName;

    private String placeOfBirth;

    private String phoneNumber;

    private String gender;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    private String sex;

    private String height;

    private String weight;

    private String status;

    private String disabilityCheck;

}
