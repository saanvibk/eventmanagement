package com.example.eventmanagement .repository;

import com.example.eventmanagement .model.EventRegistration;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EventRegistrationRepository extends JpaRepository<EventRegistration, Long> {

    List<EventRegistration> findByEventId(Long eventId);

    List<EventRegistration> findByMemberId(Long memberId);

    Optional<EventRegistration> findByEventIdAndMemberId(Long eventId, Long memberId);

    long countByEventIdAndStatus(Long eventId, EventRegistration.RegistrationStatus status);

    boolean existsByEventIdAndMemberId(Long eventId, Long memberId);
}
