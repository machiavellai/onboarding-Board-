package com.example.Ecoboard.Ecoboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "Adress")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Adress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "permanent_address", unique = true, nullable = false)
    private String permanentAddress;
    @NotNull
    @Column(name = "present_address", unique = true, nullable = false)
    private String presentAddress;

    @NotNull
    @Column(name = "permanent_telephone_no", unique = true, nullable = false)
    private String permanentTelephone_no;

    @NotNull
    @Column(name = "present_telephone_no", unique = true, nullable = false)
    private String presentTelephone_no;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @NotNull
    @Column(name = "present_nationality", unique = true, nullable = false)
    private String presentNationality;

    @NotNull
    @Column(name = "Sex", unique = true, nullable = false)
    private String  Sex;

    @NotNull
    @Column(name = "height", unique = true, nullable = false)
    private int height;

    @NotNull
    @Column(name = "weight", unique = true, nullable = false)
    private int weight;

    @NotNull
    @Column(name = "Marital_status", unique = true, nullable = false)
    private String MaritalStatus;
}
