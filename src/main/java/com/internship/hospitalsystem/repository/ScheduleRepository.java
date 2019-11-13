package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ScheduleRepository extends JpaRepository <Schedule, Long> {

}
