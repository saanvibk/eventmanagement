package com.example.eventmanagement .observer.impl;

import com.example.eventmanagement .model.Event;
import com.example.eventmanagement .observer.EventObserver;
import org.springframework.stereotype.Component;

/**
 * Observer Pattern – Concrete Observer (Email Notification)
 *
 * OCP in action: This new notification channel was added WITHOUT changing
 * EventService or any other observer. Just implement EventObserver and register.
 */
@Component
public class EmailNotificationObserver implements EventObserver {

    @Override
    public void update(Event event, String eventType) {
        // In production: inject JavaMailSender and send real emails.
        // For now, console output simulates the email dispatch.
        String subject = buildSubject(eventType, event);
        String body = buildBody(eventType, event);

        System.out.println("=== EMAIL NOTIFICATION ===");
        System.out.println("To: Club Members of Club ID " + event.getClubId());
        System.out.println("Subject: " + subject);
        System.out.println("Body: " + body);
        System.out.println("==========================");
    }

    private String buildSubject(String eventType, Event event) {
        return switch (eventType) {
            case "PUBLISHED"  -> "[ClubLink] New Event: " + event.getTitle();
            case "UPDATED"    -> "[ClubLink] Event Updated: " + event.getTitle();
            case "CANCELLED"  -> "[ClubLink] Event Cancelled: " + event.getTitle();
            case "COMPLETED"  -> "[ClubLink] Event Completed: " + event.getTitle();
            case "REMINDER"   -> "[ClubLink] Reminder: " + event.getTitle() + " is Tomorrow!";
            default           -> "[ClubLink] Event Notice: " + event.getTitle();
        };
    }

    private String buildBody(String eventType, Event event) {
        return switch (eventType) {
            case "PUBLISHED" -> String.format(
                "Hello! A new event '%s' has been published.\nDate: %s\nVenue: %s\nCategory: %s\n\nRegister now on ClubLink!",
                event.getTitle(), event.getEventDate(), event.getVenue(), event.getCategory());
            case "CANCELLED" -> String.format(
                "We regret to inform you that '%s' scheduled on %s has been CANCELLED.",
                event.getTitle(), event.getEventDate());
            case "REMINDER" -> String.format(
                "Don't forget! '%s' is happening tomorrow, %s at %s.",
                event.getTitle(), event.getEventDate(), event.getVenue());
            default -> String.format(
                "There's an update regarding the event '%s'. Please log in to ClubLink for more details.",
                event.getTitle());
        };
    }
}
