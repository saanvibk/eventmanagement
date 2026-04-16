package com.example.eventmanagement .service.impl;

import com.example.eventmanagement .dto.EventSearchDTO;
import com.example.eventmanagement .model.Event;
import com.example.eventmanagement .model.Event.EventStatus;
import com.example.eventmanagement .model.EventRegistration;
import com.example.eventmanagement .observer.EventObserver;
import com.example.eventmanagement .observer.EventSubject;
import com.example.eventmanagement .observer.impl.DashboardNotificationObserver;
import com.example.eventmanagement .observer.impl.EmailNotificationObserver;
import com.example.eventmanagement .repository.EventRegistrationRepository;
import com.example.eventmanagement .repository.EventRepository;
import com.example.eventmanagement .service.EventService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * EventServiceImpl – core business logic for Event Management.
 *
 * ✅ Design Pattern  : Observer Pattern
 *    - Implements EventSubject to manage observers.
 *    - Notifies EmailNotificationObserver and DashboardNotificationObserver
 *      on every state change (publish, cancel, complete, etc.)
 *    - New observers can be added WITHOUT modifying this class.
 *
 * ✅ Design Principle: Open/Closed Principle (OCP)
 *    - EventService interface is stable (closed for modification).
 *    - New features (e.g., caching, audit logging) → new implementation,
 *      no changes to existing code.
 *    - Search filters in EventRepository extend queries without altering
 *      existing repository methods.
 */
@Service
@Transactional
public class EventServiceImpl implements EventService, EventSubject {

    private final EventRepository eventRepository;
    private final EventRegistrationRepository registrationRepository;
    private final List<EventObserver> observers = new ArrayList<>();

    // Injected concrete observers
    private final EmailNotificationObserver emailObserver;
    private final DashboardNotificationObserver dashboardObserver;

    @Autowired
    public EventServiceImpl(EventRepository eventRepository,
                            EventRegistrationRepository registrationRepository,
                            EmailNotificationObserver emailObserver,
                            DashboardNotificationObserver dashboardObserver) {
        this.eventRepository = eventRepository;
        this.registrationRepository = registrationRepository;
        this.emailObserver = emailObserver;
        this.dashboardObserver = dashboardObserver;
    }

    /** Register all concrete observers at startup. */
    @PostConstruct
    public void initObservers() {
        registerObserver(emailObserver);
        registerObserver(dashboardObserver);
    }

    // ========================
    //  Observer Pattern Methods
    // ========================

    @Override
    public void registerObserver(EventObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    @Override
    public void removeObserver(EventObserver observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(Event event, String eventType) {
        for (EventObserver observer : observers) {
            observer.update(event, eventType);
        }
    }

    // ========================
    //  CRUD Operations
    // ========================

    @Override
    public Event createEvent(Event event) {
        event.setStatus(EventStatus.DRAFT);
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Long id, Event updatedEvent) {
        Event existing = getEventOrThrow(id);
        existing.setTitle(updatedEvent.getTitle());
        existing.setDescription(updatedEvent.getDescription());
        existing.setEventDate(updatedEvent.getEventDate());
        existing.setStartTime(updatedEvent.getStartTime());
        existing.setEndTime(updatedEvent.getEndTime());
        existing.setVenue(updatedEvent.getVenue());
        existing.setCategory(updatedEvent.getCategory());
        existing.setMaxParticipants(updatedEvent.getMaxParticipants());
        existing.setRegistrationFee(updatedEvent.getRegistrationFee());
        Event saved = eventRepository.save(existing);
        notifyObservers(saved, "UPDATED");
        return saved;
    }

    @Override
    public void deleteEvent(Long id) {
        eventRepository.deleteById(id);
    }

    @Override
    public Optional<Event> findById(Long id) {
        return eventRepository.findById(id);
    }

    @Override
    public List<Event> findAll() {
        return eventRepository.findAll();
    }

    // ========================
    //  State Transitions
    // ========================

    @Override
    public Event publishEvent(Long id) {
        Event event = getEventOrThrow(id);
        validateTransition(event.getStatus(), EventStatus.PUBLISHED, EventStatus.DRAFT);
        event.setStatus(EventStatus.PUBLISHED);
        Event saved = eventRepository.save(event);
        notifyObservers(saved, "PUBLISHED");   // 🔔 Observer fires here
        return saved;
    }

    @Override
    public Event startEvent(Long id) {
        Event event = getEventOrThrow(id);
        validateTransition(event.getStatus(), EventStatus.ONGOING, EventStatus.PUBLISHED);
        event.setStatus(EventStatus.ONGOING);
        return eventRepository.save(event);
    }

    @Override
    public Event completeEvent(Long id) {
        Event event = getEventOrThrow(id);
        validateTransition(event.getStatus(), EventStatus.COMPLETED, EventStatus.ONGOING);
        event.setStatus(EventStatus.COMPLETED);
        Event saved = eventRepository.save(event);
        notifyObservers(saved, "COMPLETED");   // 🔔 Observer fires here
        return saved;
    }

    @Override
    public Event cancelEvent(Long id) {
        Event event = getEventOrThrow(id);
        if (event.getStatus() == EventStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed event.");
        }
        event.setStatus(EventStatus.CANCELLED);
        Event saved = eventRepository.save(event);
        notifyObservers(saved, "CANCELLED");   // 🔔 Observer fires here
        return saved;
    }

    // ========================
    //  Search & Filter (Minor Feature)
    // ========================

    @Override
    public List<Event> searchEvents(EventSearchDTO dto) {
        return eventRepository.searchEvents(
                dto.getKeyword(),
                dto.getCategory(),
                dto.getFromDate(),
                dto.getToDate(),
                dto.getStatus(),
                dto.getClubId(),
                dto.getMaxFee()
        );
    }

    @Override
    public List<Event> findByClub(Long clubId) {
        return eventRepository.findByClubId(clubId);
    }

    @Override
    public List<Event> findByStatus(EventStatus status) {
        return eventRepository.findByStatus(status);
    }

    @Override
    public List<Event> findByCategory(String category) {
        return eventRepository.findByCategory(category);
    }

    // ========================
    //  Registration
    // ========================

    @Override
    public EventRegistration registerMember(Long eventId, Long memberId) {
        Event event = getEventOrThrow(eventId);

        if (event.getStatus() != EventStatus.PUBLISHED && event.getStatus() != EventStatus.ONGOING) {
            throw new IllegalStateException("Registrations are only open for published or ongoing events.");
        }
        if (registrationRepository.existsByEventIdAndMemberId(eventId, memberId)) {
            throw new IllegalStateException("Member is already registered for this event.");
        }
        long currentCount = registrationRepository.countByEventIdAndStatus(
                eventId, EventRegistration.RegistrationStatus.REGISTERED);
        if (event.getMaxParticipants() != null && currentCount >= event.getMaxParticipants()) {
            throw new IllegalStateException("Event is full. Maximum participants reached.");
        }
        return registrationRepository.save(new EventRegistration(eventId, memberId));
    }

    @Override
    public void cancelRegistration(Long eventId, Long memberId) {
        EventRegistration reg = registrationRepository.findByEventIdAndMemberId(eventId, memberId)
                .orElseThrow(() -> new IllegalArgumentException("Registration not found."));
        reg.setStatus(EventRegistration.RegistrationStatus.CANCELLED);
        registrationRepository.save(reg);
    }

    @Override
    public List<EventRegistration> getRegistrations(Long eventId) {
        return registrationRepository.findByEventId(eventId);
    }

    @Override
    public long getRegistrationCount(Long eventId) {
        return registrationRepository.countByEventIdAndStatus(
                eventId, EventRegistration.RegistrationStatus.REGISTERED);
    }

    @Override
    public boolean isMemberRegistered(Long eventId, Long memberId) {
        return registrationRepository.existsByEventIdAndMemberId(eventId, memberId);
    }

    // ========================
    //  Helpers
    // ========================

    private Event getEventOrThrow(Long id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Event not found with id: " + id));
    }

    private void validateTransition(EventStatus current, EventStatus target, EventStatus... allowed) {
        for (EventStatus s : allowed) {
            if (current == s) return;
        }
        throw new IllegalStateException(
                "Cannot transition from " + current + " to " + target + ".");
    }
}
