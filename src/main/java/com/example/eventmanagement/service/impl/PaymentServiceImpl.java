package com.example.eventmanagement.service.impl;

import com.example.eventmanagement.model.Payment;
import com.example.eventmanagement.model.Payment.PaymentStatus;
import com.example.eventmanagement.repository.PaymentRepository;
import com.example.eventmanagement.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * PaymentServiceImpl – core business logic for Payment Management.
 *
 * ✅ Design Principle: Dependency Inversion Principle (DIP)
 *    - Controller depends on the PaymentService interface, not this class.
 *    - Repository is injected via constructor.
 */
@Service
@Transactional
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;

    @Autowired
    public PaymentServiceImpl(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    // ========================
    //  CRUD
    // ========================

    @Override
    public Payment savePayment(Payment payment) {
        return paymentRepository.save(payment);
    }

    @Override
    public Optional<Payment> findById(Long id) {
        return paymentRepository.findById(id);
    }

    @Override
    public List<Payment> findAll() {
        return paymentRepository.findAll();
    }

    @Override
    public List<Payment> findByMember(Long memberId) {
        return paymentRepository.findByMemberId(memberId);
    }

    // ========================
    //  State Transitions
    // ========================

    @Override
    public Payment markPaid(Long paymentId) {
        Payment p = getOrThrow(paymentId);
        p.setStatus(PaymentStatus.PAID);
        p.setPaidAt(LocalDateTime.now());
        return paymentRepository.save(p);
    }

    @Override
    public Payment confirmPayment(Long paymentId) {
        Payment p = getOrThrow(paymentId);
        p.setStatus(PaymentStatus.CONFIRMED);
        return paymentRepository.save(p);
    }

    @Override
    public Payment failPayment(Long paymentId) {
        Payment p = getOrThrow(paymentId);
        p.setStatus(PaymentStatus.FAILED);
        return paymentRepository.save(p);
    }

    @Override
    public void deletePayment(Long id) {
        paymentRepository.deleteById(id);
    }

    // ========================
    //  Cascade deletes
    // ========================

    @Override
    public void deletePaymentByMemberAndEventId(Long memberId, Long eventId) {
        paymentRepository.deleteByMemberAndEvent(memberId, eventId);
    }

    @Override
    public void deletePaymentByMemberAndClubId(Long memberId, Long clubId) {
        paymentRepository.deleteByMemberAndClub(memberId, clubId);
    }

    // ========================
    //  Totals
    // ========================

    @Override
    public double totalByStatus(Long memberId, PaymentStatus status) {
        return paymentRepository.findByMemberIdAndStatus(memberId, status).stream()
                .map(Payment::getAmount)
                .filter(a -> a != null)
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    // ========================
    //  Fines
    // ========================

    /**
     * Stub: checks whether a payment is overdue and would emit a notification.
     * Real implementation would persist to a fine_notifications table.
     */
    @Override
    public void generateFineNotification(Long memberId, Long paymentId) {
        Payment p = getOrThrow(paymentId);
        if (p.getPaidAt() == null && p.getStatus() == PaymentStatus.PENDING) {
            // TODO(mem4): persist to fine_notifications table
            System.out.println("[FineNotification] member=" + memberId + " payment=" + paymentId);
        }
    }

    // ========================
    //  Helpers
    // ========================

    private Payment getOrThrow(Long id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id: " + id));
    }
}