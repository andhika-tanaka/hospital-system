package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Clinic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClinicRepository extends JpaRepository <Clinic, Long> {
}
