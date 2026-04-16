package com.example.eventmanagement .observer;

import com.example.eventmanagement .model.Event;

/**
 * Observer Pattern – Subject Interface
 *
 * Defines the contract for the subject (publisher) side.
 * EventService implements this to manage observers and fire notifications.
 */
public interface EventSubject {

    void registerObserver(EventObserver observer);

    void removeObserver(EventObserver observer);

    void notifyObservers(Event event, String eventType);
}
