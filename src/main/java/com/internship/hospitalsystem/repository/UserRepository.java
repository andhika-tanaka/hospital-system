package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    User findByEmail(String email);

    @Query("SELECT u, p FROM User u JOIN u.roles p WHERE NOT (p.title='PASIEN')")
    List<User> findStaffs();

    @Query("SELECT u, p FROM User u JOIN u.roles p WHERE p.title='PASIEN'")
    List<User> findPatients();

    @Query("SELECT u, p FROM User u JOIN u.roles p WHERE p.title='DOKTER'")
    List<User> findDoctors();

    @Query("SELECT u FROM User u WHERE u.address LIKE %:keywords% " +
            "OR u.firstName LIKE %:keywords% " +
            "OR u.lastName LIKE %:keywords% " +
            "OR u.email LIKE %:keywords% " +
            "OR u.phone1 LIKE %:keywords% " +
            "OR u.phone2 LIKE %:keywords%")
    List<User> findUser(String keywords);


}
