package com.example.eventmanagement.facade;

import org.springframework.stereotype.Component;

import java.time.LocalDate;

/**
 * Facade over the Event subsystem – returns a compact {@link EventDetails}
 * object so the payment report doesn't need to talk to {@code EventService}
 * directly.
 *
 * Stub: returns mock data. Replace with EventRepository lookup when Mem2
 * merges.
 */
@Component
public class EventDetailsFacade implements DetailsFacade {

    @Override
    public Details getDetails(Long eventId) {
        // TODO(mem4): delegate to EventService.findById(eventId)
        return new EventDetails(
                eventId,
                "Sample Event",
                LocalDate.now(),
                "Main Auditorium",
                250.0
        );
    }
}