package com.event.model;

public class Booking {
    private int id;
    private int eventId;
    private String username;
    private String bookedAt;

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public int getEventId() { return eventId; }
    public void setEventId(int eventId) { this.eventId = eventId; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getBookedAt() { return bookedAt; }
    public void setBookedAt(String bookedAt) { this.bookedAt = bookedAt; }
} 