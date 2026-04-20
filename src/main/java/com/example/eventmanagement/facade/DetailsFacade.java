package com.example.eventmanagement.facade;

/**
 * Facade Pattern – exposes a single {@code getDetails()} entry point
 * for subsystems (event, payment, club) that the report generator
 * would otherwise have to query individually.
 */
public interface DetailsFacade {
    Details getDetails(Long id);
}