package com.example.Ecoboard.Ecoboard.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Set;

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

//    @ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//    @JoinTable(name = "staff_images",
//            joinColumns = {
//                @JoinColumn(name = "staff_id" )
//            },
//            inverseJoinColumns = {
//                @JoinColumn(name = "image_id")
//            }
//    )
//    private Set<ImageModel> staffImages;

    private String resetPasswordToken;

    public Staff(Long id, String email, String userName, String password) {
    }

}
