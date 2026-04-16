-- ============================================================
--  ClubLink – Mem2: Event Management MySQL Schema
--  Run this on the shared MySQL database
-- ============================================================

CREATE TABLE IF NOT EXISTS events (
    id                 BIGINT       NOT NULL AUTO_INCREMENT,
    title              VARCHAR(255) NOT NULL,
    description        TEXT,
    event_date         DATE         NOT NULL,
    start_time         TIME,
    end_time           TIME,
    venue              VARCHAR(255) NOT NULL,
    category           VARCHAR(100),
    max_participants   INT,
    registration_fee   DOUBLE,
    status             ENUM('DRAFT','PUBLISHED','ONGOING','COMPLETED','CANCELLED') NOT NULL DEFAULT 'DRAFT',
    club_id            BIGINT,
    organizer_id       BIGINT,
    created_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at         DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id)
    -- FK to clubs table (Mem1): FOREIGN KEY (club_id) REFERENCES clubs(id)
    -- FK to members table (Mem3): FOREIGN KEY (organizer_id) REFERENCES members(id)
);

CREATE TABLE IF NOT EXISTS event_registrations (
    id            BIGINT NOT NULL AUTO_INCREMENT,
    event_id      BIGINT NOT NULL,
    member_id     BIGINT NOT NULL,
    status        ENUM('REGISTERED','CANCELLED','ATTENDED') NOT NULL DEFAULT 'REGISTERED',
    registered_at DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    UNIQUE KEY uq_event_member (event_id, member_id),
    FOREIGN KEY (event_id) REFERENCES events(id) ON DELETE CASCADE
    -- FOREIGN KEY (member_id) REFERENCES members(id) -- enable after Mem3 merges
);

-- ---- Sample seed data for testing ----
INSERT INTO events (title, description, event_date, start_time, end_time, venue, category, max_participants, registration_fee, status, club_id, organizer_id)
VALUES
('Annual Tech Fest 2025',   'A celebration of technology and innovation.',    '2025-09-15', '09:00:00', '17:00:00', 'Main Auditorium',  'Technical', 200, 50.00,  'PUBLISHED', 1, 1),
('Cultural Night',          'Music, dance, and drama by club members.',        '2025-10-02', '18:00:00', '21:00:00', 'Open Air Theatre',  'Cultural',  300, 0.00,   'DRAFT',     1, 1),
('Python Workshop',         'Hands-on Python for beginners.',                  '2025-08-20', '10:00:00', '13:00:00', 'Lab Block 3 Room 1','Workshop',  40,  100.00, 'COMPLETED', 2, 1),
('Inter-Club Cricket',      'Friendly cricket tournament between clubs.',       '2025-11-10', '08:00:00', '18:00:00', 'Sports Ground',     'Sports',    60,  20.00,  'PUBLISHED', 2, 1);
