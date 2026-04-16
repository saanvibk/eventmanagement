package com.example.eventmanagement .model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Event entity - represents a club event.
 * Design Principle: Open/Closed Principle (OCP)
 * - EventStatus enum allows new states without modifying this class.
 */
@Entity
@Table(name = "events")
public class Event {

    public enum EventStatus {
        DRAFT, PUBLISHED, ONGOING, COMPLETED, CANCELLED
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(length = 2000)
    private String description;

    @Column(nullable = false)
    private LocalDate eventDate;

    private LocalTime startTime;

    private LocalTime endTime;

    @Column(nullable = false)
    private String venue;

    private String category; // e.g. Technical, Cultural, Sports

    private Integer maxParticipants;

    private Double registrationFee;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EventStatus status;

    // FK to Club (created by Mem1)
    @Column(name = "club_id")
    private Long clubId;

    // FK to Member (organizer)
    @Column(name = "organizer_id")
    private Long organizerId;

    @Column(nullable = false, updatable = false)
    private java.time.LocalDateTime createdAt;

    private java.time.LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = java.time.LocalDateTime.now();
        updatedAt = java.time.LocalDateTime.now();
        if (status == null) status = EventStatus.DRAFT;
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = java.time.LocalDateTime.now();
    }

    // ---- Constructors ----
    public Event() {}

    public Event(String title, String description, LocalDate eventDate,
                 LocalTime startTime, LocalTime endTime, String venue,
                 String category, Integer maxParticipants, Double registrationFee,
                 Long clubId, Long organizerId) {
        this.title = title;
        this.description = description;
        this.eventDate = eventDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.venue = venue;
        this.category = category;
        this.maxParticipants = maxParticipants;
        this.registrationFee = registrationFee;
        this.clubId = clubId;
        this.organizerId = organizerId;
        this.status = EventStatus.DRAFT;
    }

    // ---- Getters & Setters ----
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }

    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public Integer getMaxParticipants() { return maxParticipants; }
    public void setMaxParticipants(Integer maxParticipants) { this.maxParticipants = maxParticipants; }

    public Double getRegistrationFee() { return registrationFee; }
    public void setRegistrationFee(Double registrationFee) { this.registrationFee = registrationFee; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }

    public Long getOrganizerId() { return organizerId; }
    public void setOrganizerId(Long organizerId) { this.organizerId = organizerId; }

    public java.time.LocalDateTime getCreatedAt() { return createdAt; }
    public java.time.LocalDateTime getUpdatedAt() { return updatedAt; }
}
