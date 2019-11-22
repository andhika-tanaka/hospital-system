package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Registration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RegistrationRepository extends JpaRepository <Registration, Long> {
    List<Registration> findByOrderByCheckupDateAscDoctorsAscNumberAsc();

    Registration findTopByOrderByIdDesc();

    @Query("SELECT MAX(r.number) FROM Registration r JOIN r.doctors d " +
            "WHERE r.checkupDate = ?1 " +
            "AND d.id = ?2 " +
            "ORDER BY r.number " +
            "DESC")
    Integer last_number(String date, Long doctor);

    @Query("SELECT r FROM Registration r " +
            "WHERE r.doctors.firstName LIKE %:keywords% " +
            "OR r.doctors.lastName LIKE %:keywords% " +
            "OR r.patients.firstName LIKE %:keywords% " +
            "OR r.patients.lastName LIKE %:keywords% " +
            "OR r.checkupDate LIKE %:keywords% " +
            "ORDER BY r.checkupDate, r.doctors, r.number")
    List<Registration> findRegistration(String keywords);
}
