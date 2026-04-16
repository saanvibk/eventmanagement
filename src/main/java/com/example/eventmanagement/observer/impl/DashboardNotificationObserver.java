package com.example.eventmanagement .observer.impl;

import com.example.eventmanagement .model.Event;
import com.example.eventmanagement .observer.EventObserver;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Observer Pattern – Concrete Observer (Dashboard / In-App Notification)
 *
 * Stores notifications in memory (or DB in production) for display
 * in the dashboard notification panel.
 *
 * OCP: Added as a new observer without touching EventService or EmailObserver.
 */
@Component
public class DashboardNotificationObserver implements EventObserver {

    // Simple in-memory log; replace with DB table in production.
    private final List<String> notificationLog = new ArrayList<>();

    @Override
    public void update(Event event, String eventType) {
        String message = buildMessage(eventType, event);
        String entry = "[" + LocalDateTime.now() + "] " + message;
        notificationLog.add(entry);

        System.out.println("=== DASHBOARD NOTIFICATION ===");
        System.out.println(entry);
        System.out.println("==============================");
    }

    public List<String> getRecentNotifications(int limit) {
        int size = notificationLog.size();
        if (size == 0) return Collections.emptyList();
        return notificationLog.subList(Math.max(0, size - limit), size)
                              .stream()
                              .sorted(Collections.reverseOrder())
                              .toList();
    }

    private String buildMessage(String eventType, Event event) {
        return switch (eventType) {
            case "PUBLISHED"  -> "📢 New event published: " + event.getTitle() + " on " + event.getEventDate();
            case "UPDATED"    -> "✏️ Event updated: " + event.getTitle();
            case "CANCELLED"  -> "❌ Event cancelled: " + event.getTitle();
            case "COMPLETED"  -> "✅ Event completed: " + event.getTitle();
            case "REMINDER"   -> "⏰ Tomorrow: " + event.getTitle() + " at " + event.getVenue();
            default           -> "ℹ️ Event notice: " + event.getTitle();
        };
    }
}
