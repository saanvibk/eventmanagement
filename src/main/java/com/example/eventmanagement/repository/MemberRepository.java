package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {
}