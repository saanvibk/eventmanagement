package com.example.eventmanagement .observer;

import com.example.eventmanagement .model.Event;

/**
 * Observer Pattern – Observer Interface
 *
 * Design Pattern: Observer (Behavioral)
 * Applied to: Event lifecycle notifications
 *
 * OCP Relevance: New notification types (Email, SMS, Push) can be added
 * by implementing this interface WITHOUT modifying existing observers
 * or the EventSubject — satisfying Open/Closed Principle.
 */
public interface EventObserver {

    /**
     * Called when an event's status changes or a new event is published.
     *
     * @param event     The event that triggered the notification
     * @param eventType A string describing the change: "PUBLISHED", "UPDATED",
     *                  "CANCELLED", "COMPLETED", "REMINDER"
     */
    void update(Event event, String eventType);
}
