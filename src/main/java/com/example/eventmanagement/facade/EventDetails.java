package com.example.eventmanagement.facade;

import java.time.LocalDate;

/**
 * DTO populated by {@link EventDetailsFacade} for the payment report.
 */
public class EventDetails implements Details {

    private Long id;
    private String title;
    private LocalDate eventDate;
    private String venue;
    private Double registrationFee;

    public EventDetails() {}

    public EventDetails(Long id, String title, LocalDate eventDate,
                        String venue, Double registrationFee) {
        this.id = id;
        this.title = title;
        this.eventDate = eventDate;
        this.venue = venue;
        this.registrationFee = registrationFee;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public LocalDate getEventDate() { return eventDate; }
    public void setEventDate(LocalDate eventDate) { this.eventDate = eventDate; }

    public String getVenue() { return venue; }
    public void setVenue(String venue) { this.venue = venue; }

    public Double getRegistrationFee() { return registrationFee; }
    public void setRegistrationFee(Double registrationFee) { this.registrationFee = registrationFee; }
}