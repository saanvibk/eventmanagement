package com.example.eventmanagement.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.model.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {

    // From develop branch
    List<Member> findByClubId(Long clubId);

    // From your branch (stash)
    List<Member> findByStatus(String status);
}