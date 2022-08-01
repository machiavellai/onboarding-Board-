package com.example.Ecoboard.Ecoboard.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SelfDeclerationRequest {

    private Boolean criminalRecord;

    private String criminalRecordDescription;

    private Boolean previousEmploymentDismissal;

    private String previousEmploymentDescription;

    private Boolean pastPoorCreditRating;

    private String pastPoorCreditRatingDescription;

    private Boolean pastLitigationSuits;

    private String pastLitigationSuitsDescription;

    private Boolean bankingRelatedLicenses;

    private String bankingRelatedLicensesDescription;

    private String staffName;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date date;

}
