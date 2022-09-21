package com.example.Ecoboard.Ecoboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class EmergencyContactsRequest {
    private String employeeName;

    private String jobTitle;

    private String contactName_one;

    private String contactName_one_address;

    private String contactName_one_relationship;

    private String contactName_one_number;


    private String contactName_two;

    private String contactName_two_relationship;

    private String contactName_two_number;

    private String contactName_two_address;



}
