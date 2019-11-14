package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RegistrationRepository extends JpaRepository <Registration, Long> {
}
