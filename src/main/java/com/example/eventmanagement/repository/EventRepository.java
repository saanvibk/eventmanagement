package com.example.eventmanagement .repository;

import com.example.eventmanagement .model.Event;
import com.example.eventmanagement .model.Event.EventStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

/**
 * EventRepository – Spring Data JPA repository for Event entity.
 *
 * OCP: New query methods can be added here without modifying existing ones
 * or the service layer (which depends on abstraction, not concrete queries).
 */
@Repository
public interface EventRepository extends JpaRepository<Event, Long> {

    // --- Basic finders ---
    List<Event> findByStatus(EventStatus status);

    List<Event> findByClubId(Long clubId);

    List<Event> findByOrganizerId(Long organizerId);

    List<Event> findByCategory(String category);

    // --- Search/Filter (Minor Feature) ---
    @Query("""
        SELECT e FROM Event e
        WHERE (:keyword IS NULL OR :keyword = ''
               OR LOWER(e.title) LIKE LOWER(CONCAT('%', :keyword, '%'))
               OR LOWER(e.description) LIKE LOWER(CONCAT('%', :keyword, '%')))
          AND (:category IS NULL OR :category = '' OR e.category = :category)
          AND (:fromDate IS NULL OR e.eventDate >= :fromDate)
          AND (:toDate IS NULL OR e.eventDate <= :toDate)
          AND (:status IS NULL OR e.status = :status)
          AND (:clubId IS NULL OR e.clubId = :clubId)
          AND (:maxFee IS NULL OR e.registrationFee <= :maxFee)
        ORDER BY e.eventDate ASC
    """)
    List<Event> searchEvents(
            @Param("keyword")  String keyword,
            @Param("category") String category,
            @Param("fromDate") LocalDate fromDate,
            @Param("toDate")   LocalDate toDate,
            @Param("status")   EventStatus status,
            @Param("clubId")   Long clubId,
            @Param("maxFee")   Double maxFee
    );

    // --- State transitions / lifecycle queries ---
    List<Event> findByEventDateAndStatus(LocalDate date, EventStatus status);

    List<Event> findByEventDateBeforeAndStatus(LocalDate date, EventStatus status);

    List<Event> findByClubIdAndStatus(Long clubId, EventStatus status);
}
