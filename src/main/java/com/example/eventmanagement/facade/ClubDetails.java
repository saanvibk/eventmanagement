package com.example.eventmanagement.facade;

/**
 * DTO populated by {@link ClubDetailsFacade} for the payment report.
 */
public class ClubDetails implements Details {

    private Long id;
    private String name;
    private String category;
    private String leaderName;

    public ClubDetails() {}

    public ClubDetails(Long id, String name, String category, String leaderName) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.leaderName = leaderName;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getLeaderName() { return leaderName; }
    public void setLeaderName(String leaderName) { this.leaderName = leaderName; }
}