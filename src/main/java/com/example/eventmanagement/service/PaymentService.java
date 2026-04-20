package com.example.eventmanagement.service;

import com.example.eventmanagement.model.Payment;
import com.example.eventmanagement.model.Payment.PaymentStatus;

import java.util.List;
import java.util.Optional;

/**
 * PaymentService – contract for payment management (Mem4).
 *
 * Responsibilities:
 *   1) Save payments to the database.
 *   2) List payments made by a member (member dashboard).
 *   3) Remove payments if a member unregisters from an event or leaves a club.
 *   4) Generate fine notifications for overdue payments.
 */
public interface PaymentService {

    // CRUD
    Payment savePayment(Payment payment);

    Optional<Payment> findById(Long id);

    List<Payment> findAll();

    List<Payment> findByMember(Long memberId);

    // State transitions
    Payment markPaid(Long paymentId);

    Payment confirmPayment(Long paymentId);

    Payment failPayment(Long paymentId);

    void deletePayment(Long id);

    // Cascade deletes
    void deletePaymentByMemberAndEventId(Long memberId, Long eventId);

    void deletePaymentByMemberAndClubId(Long memberId, Long clubId);

    // Totals for dashboard tiles
    double totalByStatus(Long memberId, PaymentStatus status);

    // Fines (minor feature)
    void generateFineNotification(Long memberId, Long paymentId);
}