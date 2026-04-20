package com.example.eventmanagement.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tracks payments made by members for events, club memberships, dues and fines.
 *
 * Mem4 – Payment Management.
 */
@Entity
@Table(name = "payments")
public class Payment {

    public enum PaymentType {
        REGISTRATION_FEE, MEMBERSHIP_FEE, FINE, DUES
    }

    public enum PaymentStatus {
        PENDING, PAID, CONFIRMED, FAILED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id")
    private Long eventId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Column(name = "club_id")
    private Long clubId;

    @Column(name = "amount", nullable = false)
    private Double amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentType type;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        createdAt = now;
        updatedAt = now;
        if (status == null) status = PaymentStatus.PENDING;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    public Payment() {}

    public Payment(Long memberId, Long eventId, Long clubId, Double amount, PaymentType type) {
        this.memberId = memberId;
        this.eventId = eventId;
        this.clubId = clubId;
        this.amount = amount;
        this.type = type;
        this.status = PaymentStatus.PENDING;
    }

    // ========================
    //  Getters & Setters
    // ========================

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }

    public Double getAmount() { return amount; }
    public void setAmount(Double amount) { this.amount = amount; }

    public PaymentType getType() { return type; }
    public void setType(PaymentType type) { this.type = type; }

    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }

    public LocalDateTime getPaidAt() { return paidAt; }
    public void setPaidAt(LocalDateTime paidAt) { this.paidAt = paidAt; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}