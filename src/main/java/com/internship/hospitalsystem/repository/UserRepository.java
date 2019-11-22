package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("SELECT u, p FROM User u JOIN u.positions p WHERE NOT (p.title='pasien')")
    List<User> findStaffs();

    @Query("SELECT u, p FROM User u JOIN u.positions p WHERE p.title='pasien'")
    List<User> findPatients();

    @Query("SELECT u, p FROM User u JOIN u.positions p WHERE p.title='dokter'")
    List<User> findDoctors();

    @Query("SELECT u FROM User u WHERE u.address LIKE %:keywords% " +
            "OR u.firstName LIKE %:keywords% " +
            "OR u.lastName LIKE %:keywords% " +
            "OR u.phone1 LIKE %:keywords% " +
            "OR u.phone2 LIKE %:keywords%")
    List<User> findUser(String keywords);
}
