package com.example.eventmanagement .model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tracks which members have registered for which events.
 */
@Entity
@Table(name = "payments",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "member_id"}))
       //The @UniqueConstraint annotation is for annotating multiple unique keys at the table level
       //The combination of "event_id" and "member_id" should be unique?
public class PaymentRegistration {

    public enum PaymentType {
        REGISTRATION_FEE, MEMBERSHIP_FEE, FINE, DUES
    }

    public enum PaymentStatus {
        PENDING, PAID, CONFIRMED, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "club_id", nullable = false)
    private Long clubId;

    @Column(name = "amount", nullable = false)
    private Double Amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime paidAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, updatable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        if (status == null) status = RegistrationStatus.REGISTERED;
    }

    public PaymentRegistration() {}

    public PaymentRegistration(Long eventId, Long memberId, Long clubId) {
        this.eventId = eventId;
        this.memberId = memberId;
        this.clubId = clubId;
        this.status = PaymentStatus.PAID;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getPaidAt() { return PaidAt; }
}
