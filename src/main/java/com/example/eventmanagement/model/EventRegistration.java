package com.example.eventmanagement .model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

/**
 * Tracks which members have registered for which events.
 */
@Entity
@Table(name = "event_registrations",
       uniqueConstraints = @UniqueConstraint(columnNames = {"event_id", "member_id"}))
public class EventRegistration {

    public enum RegistrationStatus {
        REGISTERED, CANCELLED, ATTENDED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "event_id", nullable = false)
    private Long eventId;

    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private RegistrationStatus status;

    @Column(nullable = false, updatable = false)
    private LocalDateTime registeredAt;

    @PrePersist
    protected void onCreate() {
        registeredAt = LocalDateTime.now();
        if (status == null) status = RegistrationStatus.REGISTERED;
    }

    public EventRegistration() {}

    public EventRegistration(Long eventId, Long memberId) {
        this.eventId = eventId;
        this.memberId = memberId;
        this.status = RegistrationStatus.REGISTERED;
    }

    // Getters & Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getEventId() { return eventId; }
    public void setEventId(Long eventId) { this.eventId = eventId; }

    public Long getMemberId() { return memberId; }
    public void setMemberId(Long memberId) { this.memberId = memberId; }

    public RegistrationStatus getStatus() { return status; }
    public void setStatus(RegistrationStatus status) { this.status = status; }

    public LocalDateTime getRegisteredAt() { return registeredAt; }
}
