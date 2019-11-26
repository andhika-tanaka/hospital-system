package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleRepository extends JpaRepository <Role, Long> {

    @Query("SELECT p FROM Role p WHERE p.title LIKE %:keywords% OR p.name LIKE %:keywords%")
    List<Role> findRole(String keywords);

    @Query("SELECT p FROM Role p WHERE NOT p.title='PASIEN'")
    List<Role> findStaffs();

    @Query("SELECT p FROM Role p WHERE p.title='PASIEN'")
    List<Role> findPatients();
}
