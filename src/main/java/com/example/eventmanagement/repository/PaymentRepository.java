package com.example.eventmanagement.repository;

import com.example.eventmanagement.model.Payment;
import com.example.eventmanagement.model.Payment.PaymentStatus;
import com.example.eventmanagement.model.Payment.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface PaymentRepository extends JpaRepository<Payment, Long> {

    List<Payment> findByEventId(Long eventId);

    List<Payment> findByMemberId(Long memberId);

    List<Payment> findByClubId(Long clubId);

    List<Payment> findByMemberIdAndStatus(Long memberId, PaymentStatus status);

    List<Payment> findByMemberIdAndType(Long memberId, PaymentType type);

    Optional<Payment> findByEventIdAndMemberId(Long eventId, Long memberId);

    Optional<Payment> findByClubIdAndMemberId(Long clubId, Long memberId);

    boolean existsByEventIdAndMemberId(Long eventId, Long memberId);

    boolean existsByClubIdAndMemberId(Long clubId, Long memberId);

    // Removes a member's payment for a specific event (e.g., registration cancelled).
    @Modifying
    @Transactional
    @Query("delete from Payment p where p.memberId = ?1 and p.eventId = ?2")
    void deleteByMemberAndEvent(Long memberId, Long eventId);

    // Removes a member's payment for a specific club (e.g., member left club).
    @Modifying
    @Transactional
    @Query("delete from Payment p where p.memberId = ?1 and p.clubId = ?2")
    void deleteByMemberAndClub(Long memberId, Long clubId);
}