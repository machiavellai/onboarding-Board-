package com.example.Ecoboard.Ecoboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

//@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
public class Staff {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_name", unique = true, nullable = false)
    private String userName;

//
//    @Column(name = "first_name", nullable = false)
//    private String firstName;
//
//
//    @Column(name = "last_name", nullable = false)
//    private String lastName;


    @Column(unique = true, nullable = false)
    private String email;


    @Column(nullable = false)
    private String password;

//
//    @Column(name = "phone_number", nullable = false)
//    private String phoneNumber;
//
//
    @Column(name = "verify_email", nullable = false)
    private boolean verifyEmail = false;

//
//    @Column(nullable = false)
//    private String gender;

   
//    @Column(name = "date_of_birth", nullable = false)
//    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
//    private Date dateOfBirth;
//
//    private String image;
//
    private String resetPasswordToken;

    public Staff(Long id, String email, String userName, String password) {
    }

}
