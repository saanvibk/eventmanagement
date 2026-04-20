package com.example.eventmanagement .repository;

import com.example.eventmanagement .model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//JpaRepository is an interface in Spring Data JPA that makes working with databases much easier.

@Repository
public interface PaymentRegistrationRepository extends JpaRepository<PaymentRegistration,Long> {
    List<PaymentRegistration> findByEventId(Long eventId);

    List<PaymentRegistration> findByMemberId(Long memberId);

    List<PaymentRegistration> findByClubId(Long clubId);

    Optional<PaymentRegistration> findByEventIdAndMemberId(Long eventId, Long memberId);

    Optional<PaymentRegistration> findByClubIdAndMemberId(Long clubId, Long memberId);
    
    boolean existsByEventIdAndMemberId(Long eventId, Long memberId);

    boolean existsByClubIdAndMemberId(Long clubId, Long memberId);
}