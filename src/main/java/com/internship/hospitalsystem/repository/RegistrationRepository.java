package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Registration;
import com.internship.hospitalsystem.model.Registration.status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface RegistrationRepository extends JpaRepository <Registration, Long> {
    @Modifying
    @Query("UPDATE Registration r SET r.status=?3 WHERE r.id =?1 and r.status=?2")
    int updateStatus(Long id, status status, status updatedStatus);
}
