package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClinicRepository extends JpaRepository <Clinic, Long> {
    @Query("SELECT c FROM Clinic c WHERE c.name LIKE %:keywords% OR c.number LIKE %:keywords%")
    List<Clinic> findClinic(String keywords);
}
