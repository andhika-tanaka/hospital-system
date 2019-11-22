package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface ScheduleRepository extends JpaRepository <Schedule, Long> {
    @Query("SELECT s FROM Schedule s " +
            "WHERE s.startHour LIKE %:keywords% " +
            "OR s.finishHour LIKE %:keywords% " +
            "OR s.clinics.number LIKE %:keywords% " +
            "OR s.clinics.name LIKE %:keywords% " +
            "OR s.users.firstName LIKE %:keywords% " +
            "OR s.users.lastName LIKE %:keywords%")
    List<Schedule> findSchedule(String keywords);
}
