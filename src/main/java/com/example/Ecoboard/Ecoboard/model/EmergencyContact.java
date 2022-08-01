package com.example.Ecoboard.Ecoboard.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity(name = "EMERGENCY_CONTACTS")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    @Column(name = "employee_name", unique = true, nullable = false)
    private String employeeName;

    @NotNull
    @Column(name = "job_title", unique = true, nullable = false)
    private String jobTitle;

    @NotNull
    @Column(name = "contact_one", unique = true, nullable = false)
    private String contactName_one;

    @NotNull
    @Column(name = "contactName_one_relation", unique = true, nullable = false)
    private String contactName_one_relationship;

    @NotNull
    @Column(name = "contactName_one_phone", unique = true, nullable = false)
    private String contactName_one_number;

    @NotNull
    @Column(name = "contactName_one_address", unique = true, nullable = false)
    private String contactName_one_address;

    @NotNull
    @Column(name = "contact_two", unique = true, nullable = false)
    private String contactName_two;

    @NotNull
    @Column(name = "contactName_two_relation", unique = true, nullable = false)
    private String contactName_two_relationship;

    @NotNull
    @Column(name = "contactName_two_phone", unique = true, nullable = false)
    private String contactName_two_number;

    @NotNull
    @Column(name = "contactName_two_address", unique = true, nullable = false)
    private String contactName_two_address;

    @OneToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "staff_id", referencedColumnName = "id", nullable = false)
    private Staff staff;

    private LocalDateTime date_created;

}
