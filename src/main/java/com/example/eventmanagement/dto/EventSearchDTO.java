package com.example.eventmanagement .dto;

import com.example.eventmanagement .model.Event.EventStatus;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

/**
 * DTO for event search/filter requests.
 * Minor Feature: Event Search/Filter (Mem2)
 */
public class EventSearchDTO {

    private String keyword;       // searches title and description

    private String category;      // Technical, Cultural, Sports, etc.

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate fromDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    private LocalDate toDate;

    private EventStatus status;

    private Long clubId;

    private Double maxFee;

    // Getters & Setters
    public String getKeyword() { return keyword; }
    public void setKeyword(String keyword) { this.keyword = keyword; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public LocalDate getFromDate() { return fromDate; }
    public void setFromDate(LocalDate fromDate) { this.fromDate = fromDate; }

    public LocalDate getToDate() { return toDate; }
    public void setToDate(LocalDate toDate) { this.toDate = toDate; }

    public EventStatus getStatus() { return status; }
    public void setStatus(EventStatus status) { this.status = status; }

    public Long getClubId() { return clubId; }
    public void setClubId(Long clubId) { this.clubId = clubId; }

    public Double getMaxFee() { return maxFee; }
    public void setMaxFee(Double maxFee) { this.maxFee = maxFee; }
}
