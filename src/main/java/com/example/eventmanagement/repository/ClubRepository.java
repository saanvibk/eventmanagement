package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Club;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClubRepository extends JpaRepository<Club, Long> {
}