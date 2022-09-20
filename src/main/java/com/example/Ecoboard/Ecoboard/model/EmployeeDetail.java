package com.example.Ecoboard.Ecoboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Entity(name = "emp_details")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmployeeDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "family_name", unique = true, nullable = false)
    private String familyName;


    @NotNull
    @Column(name = "full_name", unique = true, nullable = false)
    private String fullName;

    @NotNull
    @Column(name = "date_of_birth", nullable = false)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    private Date dateOfBirth;

    @NotNull
    @Column(name = "place_of_birth", unique = true, nullable = false)
    private String placeOfBirth;

    @NotNull
    @Column(name = "sex", unique = true, nullable = false)
    private String sex;

    @NotNull
    @Column(name = "height", unique = true, nullable = false)
    private int height;

    @NotNull
    @Column(name = "weight", unique = true, nullable = false)
    private int weight;

    @NotNull
    @Column(name = "status", unique = true, nullable = false)
    private String status;

    @NotNull
    @Column(name = "disability_check", unique = true, nullable = false)
    private String disabilityCheck;

//    @OneToOne(cascade = CascadeType.ALL, optional = false)
//    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
//    private Staff staff;

}
