package com.example.eventmanagement .service;

import com.example.eventmanagement .dto.EventSearchDTO;
import com.example.eventmanagement .model.Event;
import com.example.eventmanagement .model.Event.EventStatus;
import com.example.eventmanagement .model.EventRegistration;

import java.util.List;
import java.util.Optional;

/**
 * EventService Interface – defines event management operations.
 *
 * OCP: This interface is closed for modification.
 * New implementations (e.g., CachedEventService) can be added
 * without touching EventServiceImpl or EventController.
 */
public interface EventService {

    // --- CRUD ---
    Event createEvent(Event event);

    Event updateEvent(Long id, Event updatedEvent);

    void deleteEvent(Long id);

    Optional<Event> findById(Long id);

    List<Event> findAll();

    // --- Lifecycle transitions (State Diagram) ---
    Event publishEvent(Long id);       // DRAFT -> PUBLISHED

    Event startEvent(Long id);         // PUBLISHED -> ONGOING

    Event completeEvent(Long id);      // ONGOING -> COMPLETED

    Event cancelEvent(Long id);        // any -> CANCELLED

    // --- Search & Filter (Minor Feature) ---
    List<Event> searchEvents(EventSearchDTO searchDTO);

    List<Event> findByClub(Long clubId);

    List<Event> findByStatus(EventStatus status);

    List<Event> findByCategory(String category);

    // --- Registration ---
    EventRegistration registerMember(Long eventId, Long memberId);

    void cancelRegistration(Long eventId, Long memberId);

    List<EventRegistration> getRegistrations(Long eventId);

    long getRegistrationCount(Long eventId);

    boolean isMemberRegistered(Long eventId, Long memberId);
}
