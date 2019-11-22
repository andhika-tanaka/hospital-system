package com.internship.hospitalsystem.repository;

import com.internship.hospitalsystem.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository <Position, Long> {
    @Query("SELECT p FROM Position p WHERE p.title LIKE %:keywords% OR p.name LIKE %:keywords%")
    List<Position> findPosition(String keywords);

    List<Position> findAllByTitle(String title);
}
