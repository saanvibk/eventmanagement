package com.example.eventmanagement.facade;

import org.springframework.stereotype.Component;

/**
 * Facade over the Club subsystem – returns a compact {@link ClubDetails}
 * object for the payment report.
 *
 * Stub: returns mock data. Replace with ClubService lookup when Mem1 merges.
 */
@Component
public class ClubDetailsFacade implements DetailsFacade {

    @Override
    public Details getDetails(Long clubId) {
        // TODO(mem4): delegate to ClubService.findById(clubId)
        return new ClubDetails(
                clubId,
                "Sample Club",
                "Technical",
                "Club Leader"
        );
    }
}