package com.example.Ecoboard.Ecoboard.repository;

import java.util.Optional;

import com.example.Ecoboard.Ecoboard.model.Staff;
import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;

@Repository
public interface  StaffRepository extends JpaRepository<Staff, Long>{
    Optional<Staff> findPersonByPassword(String password);
    Optional<Staff> findPersonByUserName(String userName);
    Optional<Staff> findPersonByUserNameAndPassword(String userName,String password);

    Optional<Staff> findByUserName(String userName);
    Optional<Staff> findByEmail(String email);
    Optional<Staff> findByResetPasswordToken(String email);
}

