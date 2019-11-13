package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository <User, Long> {
    @Query("SELECT u, p FROM User u JOIN u.positions p WHERE p.title='staff'")
    List<User> findStaffs();

    @Query("SELECT u, p FROM User u JOIN u.positions p WHERE p.title='patient'")
    List<User> findPatients();

    @Query("SELECT u, p FROM User u JOIN u.positions p WHERE p.name='doctor'")
    List<User> findDoctors();
}
