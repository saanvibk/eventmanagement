package com.example.eventmanagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.eventmanagement.model.MembershipRequest;

public interface MembershipRequestRepository extends JpaRepository<MembershipRequest, Long> {
}